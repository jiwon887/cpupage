package com.jbnucpu.www.service;

import com.jbnucpu.www.repository.ContentRepository;
import com.jbnucpu.www.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteService {

    private final NoticeRepository noticeRepository;

    private final ContentRepository contentRepository;

    public void processNoticeDelete(Long id){

        noticeRepository.deleteById(id);

    }

    public void processContentDelete(Long id){

        contentRepository.deleteById(id);
    }
}
