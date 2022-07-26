package com.spring.ribborn.service;

import com.spring.ribborn.dto.requestDto.NoticeRequestDto;
import com.spring.ribborn.exception.AdminOnlyException;
import com.spring.ribborn.model.Notice;
import com.spring.ribborn.repository.NoticeRepository;
import com.spring.ribborn.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public Boolean checkAdmin(UserDetailsImpl userDetails) {
        return Objects.equals(userDetails.getUser().getUsername(), "admin");
    }

    @Transactional
    public void createNotice(NoticeRequestDto requestDto, UserDetailsImpl userDetails) throws IOException {
        if (!checkAdmin(userDetails)){
            throw new AdminOnlyException("관리자만 접근 가능합니다.");
        }

        Notice notice = new Notice(requestDto.getTitle(), requestDto.getContent());
        noticeRepository.save(notice);
    }

    @Transactional
    public void editNotice(NoticeRequestDto requestDto, UserDetailsImpl userDetails, Long noticeId) throws IOException {
        if (!checkAdmin(userDetails)){
            throw new AdminOnlyException("관리자만 접근 가능합니다.");
        }
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new IllegalArgumentException("공지가 존재하지 않습니다.")
        );

        notice.updateNotice(requestDto.getTitle(), requestDto.getContent());
        noticeRepository.save(notice);
    }

    @Transactional
    public void deleteNotice(UserDetailsImpl userDetails, Long noticeId) {
        if (!checkAdmin(userDetails)) {
            throw new AdminOnlyException("관리자만 접근 가능합니다.");
        }
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                () -> new IllegalArgumentException("공지가 존재하지 않습니다.")
        );
        noticeRepository.delete(notice);
    }

    @Transactional
    public Optional<Notice> getNotice(Long noticeId) {
        return noticeRepository.findById(noticeId);
    }

    @Transactional
    public Page<Notice> allNotice(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }
}
