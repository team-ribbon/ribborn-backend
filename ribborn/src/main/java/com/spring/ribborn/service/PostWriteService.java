package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.LookBookPostWriteDto;
import com.spring.ribborn.dto.requestDto.PostWriteRequestDto;
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

    //게시글 작성
    @Transactional
    public void postWrite(PostWriteRequestDto postWriteRequestDto) {
        User user = userRepository.findByUsername(postWriteRequestDto.getUsername()).orElse(null);
        postWriteRepository.postWrite(postWriteRequestDto,user);
    }

    //룩북 작성
    @Transactional
    public void lookBookPostWrite(LookBookPostWriteDto lookBookPostWriteDto) {
        User user = userRepository.findByUsername(lookBookPostWriteDto.getUsername()).orElse(null);
        postWriteRepository.lookBookPostWrite(lookBookPostWriteDto,user);
    }
}
