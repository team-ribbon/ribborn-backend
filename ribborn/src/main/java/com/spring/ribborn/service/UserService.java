package com.spring.ribborn.service;

import com.spring.ribborn.dto.UserRequestDto;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
