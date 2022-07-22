package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LoginRequestDto;
import com.spring.ribborn.dto.requestDto.UserRequestDto;
import com.spring.ribborn.dto.requestDto.UserUpdateRequestDto;
import com.spring.ribborn.dto.responseDto.MainPostDto;
import com.spring.ribborn.dto.responseDto.UserInfoDto;
import com.spring.ribborn.dto.responseDto.UserResponseDto;
import com.spring.ribborn.dto.responseDto.UserTokenResponseDto;
import com.spring.ribborn.model.Post;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.PostDetailRepository;
import com.spring.ribborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder encoder;
    private final PostDetailRepository postDetailRepository;

    //회원 가입 처리
    @Transactional
    public void registerUser(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());

        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 닉네임이 존재합니다.");
        }
        // 패스워드 인코딩

        // 유저 정보 저장
        if(userRequestDto.getUserType() == 1){
            //기술자 회원
            User user = User.createExpertUser(
                    userRequestDto.getUsername(),
                    password,
                    userRequestDto.getNickname(),
                    userRequestDto.getUserType(),
                    userRequestDto.getPhoneNum(),
                    userRequestDto.getCompanyNum(),
                    userRequestDto.getAddressCategory(),
                    userRequestDto.getAddressDetail(),
                    userRequestDto.getIntroduction()
            );
            userRepository.save(user);

        }else{
            //일반 회원
            User user = User.createGeneralUser(
                    userRequestDto.getUsername(),
                    password,
                    userRequestDto.getNickname(),
                    userRequestDto.getUserType(),
                    userRequestDto.getPhoneNum()
            );
            userRepository.save(user);

        }
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

    /*//아이디 중복 체크
    public void useridCheck(LoginRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 아이디가 존재합니다.");
        }
    }*/

    // 유저 정보 조회 토큰
    public UserTokenResponseDto userAuth(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        return new UserTokenResponseDto(user);
    }

    // 유저 상세 정보 , 마이 페이지
    public UserResponseDto userInfo(Long id, String postCate) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        UserResponseDto userResponseDto = new UserResponseDto();
        UserInfoDto userInfoDto = new UserInfoDto(user);
        userResponseDto.setUsers(userInfoDto);

        if(postCate.equals("all")){
            List<Post> qna1 = postDetailRepository.findMyPost("qna", id);
            List<Post> review1 = postDetailRepository.findMyPost("review", id);
            List<Post> lookbook1 = postDetailRepository.findMyPost("lookbook", id);
            List<Post> reform1 = postDetailRepository.findMyPost("reform", id);

            List<MainPostDto> qna = qna1.stream()
                    .map(MainPostDto::new)
                    .collect(Collectors.toList());
            List<MainPostDto> review = review1.stream()
                    .map(MainPostDto::new)
                    .collect(Collectors.toList());
            List<MainPostDto> lookbook = lookbook1.stream()
                    .map(MainPostDto::new)
                    .collect(Collectors.toList());
            List<MainPostDto> reform = reform1.stream()
                    .map(MainPostDto::new)
                    .collect(Collectors.toList());

            userResponseDto.setQnaList(qna);
            userResponseDto.setReviewList(review);
            userResponseDto.setLookbookList(lookbook);
            userResponseDto.setReformList(reform);
        }else{
            List<Post> posts = postDetailRepository.findMyPost(postCate, id);
            List<MainPostDto> post = posts.stream()
                    .map(MainPostDto::new)
                    .collect(Collectors.toList());
            userResponseDto.setPosts(post);
        }

        return userResponseDto;
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
            if(password != null){
                userUpdateRequestDto.setNewPassword(passwordEncoder.encode(password));
            }
            user.updateUser(
                    userUpdateRequestDto.getNewPassword(),
                    userUpdateRequestDto.getNickname(),
                    userUpdateRequestDto.getCompanyNum(),
                    userUpdateRequestDto.getPhoneNum(),
                    userUpdateRequestDto.getAddressCategory(),
                    userUpdateRequestDto.getAddressDetail(),
                    user.getIntroduction()
            );

        }else{
            throw new IllegalArgumentException("기존 비밀번호가 틀렸습니다");
        }
    }

}
