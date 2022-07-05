package com.spring.ribborn.service;

import com.spring.ribborn.dto.PostWriteRequestDto;
import com.spring.ribborn.model.User;
import com.spring.ribborn.repository.PostWriteRepository;
import com.spring.ribborn.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostWriteService {
    private final PostWriteRepository postWriteRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postWrite(PostWriteRequestDto postWriteRequestDto) {
        User user = userRepository.findByUsername(postWriteRequestDto.getUsername()).orElse(null);
        postWriteRepository.postWrite(postWriteRequestDto,user);
    }
}
