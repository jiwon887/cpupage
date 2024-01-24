package com.jbnucpu.www.service;

import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.repository.ContentRepository;
import com.jbnucpu.www.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReadService {

    private final NoticeRepository noticeRepository;

    private final ContentRepository contentRepository;

    public NoticeEntity processNoticeRead(Long id) {
        Optional<NoticeEntity> notice = this.noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        } else {
            throw new RuntimeException(); // 해당 공지글이 존재하지 않을 경우
        }
    }

    public ContentEntity processContentRead(Long id) {
        Optional<ContentEntity> content = this.contentRepository.findById(id);
        if (content.isPresent()) {
            return content.get();
        } else {
            throw new RuntimeException(); // 해당 자유글이 존재하지 않을 경우
        }
    }
}
