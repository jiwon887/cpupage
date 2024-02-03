package com.jbnucpu.www.service;

import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.repository.ContentRepository;
import com.jbnucpu.www.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class DeleteService {

    private final NoticeRepository noticeRepository;

    private final ContentRepository contentRepository;

    private final AuthService authService;

    public Boolean processNoticeDelete(Long id){

        if(!authService.isAuthenticated()){
            System.out.println("삭제 실패: 로그인 한 사용자만 삭제 가능");
            return false;
        }

        NoticeEntity noticeEntity = noticeRepository.findById(id).orElseThrow(()->new NoSuchElementException("해당 id에 대한 게시글을 찾을 수 없습니다"));
        if(!authService.getUsername().equals(noticeEntity.getUserEntity().getStudentnumber())){
            System.out.println("삭제 실패: 작성자만 삭제 가능");
            return false;
        }

        noticeRepository.deleteById(id);

        return true;
    }

    public Boolean processContentDelete(Long id){

        if(!authService.isAuthenticated()){
            System.out.println("삭제 실패: 로그인 한 사용자만 삭제 가능");
            return false;
        }
        // 작성자만 삭제 가능
        ContentEntity contentEntity = contentRepository.findById(id).orElseThrow(()->new NoSuchElementException("해당 id에 대한 게시글을 찾을 수 없습니다"));
        if(!authService.getUsername().equals(contentEntity.getStudentNumber())){
            System.out.println("삭제 실패: 작성자만 삭제 가능");
            return false;
        }


        contentRepository.deleteById(id);

        return true;
    }
}
