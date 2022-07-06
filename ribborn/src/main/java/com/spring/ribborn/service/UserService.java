package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LoginRequestDto;
import com.spring.ribborn.dto.requestDto.UserRequestDto;
import com.spring.ribborn.dto.requestDto.UserUpdateRequestDto;
import com.spring.ribborn.dto.responseDto.UserResponseDto;
import com.spring.ribborn.dto.responseDto.UserTokenResponseDto;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder encoder;

    //회원 가입 처리
    public void registerUser(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();
        String nickname = userRequestDto.getNickname();
        int userType = userRequestDto.getUserType();
        String companyNum = userRequestDto.getCompanyNum();
        String phoneNum = userRequestDto.getPhoneNum();
        String addressCategory = userRequestDto.getAddressCategory();
        String addressDetail = userRequestDto.getAddressDetail();
        String introduction = userRequestDto.getIntroduction();

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 닉네임이 존재합니다.");
        }
        // 패스워드 인코딩
        password = passwordEncoder.encode(password);

        // 유저 정보 저장
        User user = new User(username , password, nickname ,
                            userType , companyNum , phoneNum,
                            addressCategory , addressDetail, introduction);
        System.out.println("user = " + user);
        userRepository.save(user);

    }

    // 로그인
    public Boolean login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername())
                .orElse(null);
        if (user != null) {
            if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    //아이디 중복 체크
    public void useridCheck(LoginRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 아이디가 존재합니다.");
        }
    }

    // 유저 정보 조회 토큰
    public UserTokenResponseDto userAuth(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        return new UserTokenResponseDto(user);
    }

    // 유저 상세 정보 , 마이 페이지
    public UserResponseDto userInfo(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        return new UserResponseDto(user);
    }

//    // 마이페이지
//    public UserResponseDto userMyPage(Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(
//                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
//        );
//        return new UserResponseDto(user);
//    }
//

    // 유저 정보 수정
    @Transactional
    public void updateUser(UserUpdateRequestDto userUpdateRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        // 비밀번호 복호화 코드  if(encoder.matches( 입력받은 비교할 비밀번호, 이미 암호화된 비밀번호) )
        if(encoder.matches(userUpdateRequestDto.getCurrentPassword() , user.getPassword() )) {
            String password = userUpdateRequestDto.getNewPassword();
            //비밀번호 다시 암호화
            userUpdateRequestDto.setNewPassword(passwordEncoder.encode(password));
            user.update(userUpdateRequestDto);
        }else{
            throw new IllegalArgumentException("기존 비밀번호가 틀렸습니다");
        }
    }

}
