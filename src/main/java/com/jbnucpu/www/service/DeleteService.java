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

    private final AuthService authService;

    public void processNoticeDelete(Long id){

        noticeRepository.deleteById(id);

    }

    public Boolean processContentDelete(Long id){

        if(!authService.isAuthenticated()){
            System.out.println("삭제 실패: 로그인 한 사용자 본인만 삭제 가능");
            return false;
        }
        // TODO: 작성자만 삭제 가능
        contentRepository.deleteById(id);

        return true;
    }
}
