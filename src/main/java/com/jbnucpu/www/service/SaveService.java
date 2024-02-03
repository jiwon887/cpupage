package com.jbnucpu.www.service;

import com.jbnucpu.www.dto.ArticleDTO;
import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.entity.UserEntity;
import com.jbnucpu.www.repository.ContentRepository;
import com.jbnucpu.www.repository.NoticeRepository;
import com.jbnucpu.www.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveService {

    private final NoticeRepository noticeRepository;

    private final ContentRepository contentRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    //공지사항 저장
    public Boolean processNoticeSave(ArticleDTO articleDTO){

        String title = articleDTO.getTitle();
        String content = articleDTO.getContent();

        // 제목 유효한지
        if(title == null){
            return false;
        }else if(title.isEmpty()){
            return false;
        }else if(title.isBlank()){
            return false;
        }

        // 내용 유효한지
        if(content == null){
            return false;
        }else if(content.isEmpty()){
            return false;
        }else if(content.isBlank()){
            return false;
        }

        NoticeEntity noticeEntity = articleDTO.toNoticeEntity();

        String loginStudentNumber = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity loginUser = userRepository.findByStudentnumber(loginStudentNumber);

        noticeEntity.setUserEntity(loginUser);

        noticeRepository.save(noticeEntity);

        return true;
    }
    
    //자유글 저장
    public Boolean processContentSave(ArticleDTO articleDTO){

        String title = articleDTO.getTitle();
        String content = articleDTO.getContent();

        if(!authService.isAuthenticated()){
            System.out.println("저장 실패: 로그인 한 사용자만 저장가능");
            return false;
        }
        
        // 제목 유효한지
        if(title == null){
            return false;
        }else if(title.isEmpty()){
            return false;
        }else if(title.isBlank()){
            return false;
        }

        // 내용 유효한지
        if(content == null){
            return false;
        }else if(content.isEmpty()){
            return false;
        }else if(content.isBlank()){
            return false;
        }

        ContentEntity contentEntity = articleDTO.toContentEntity();

        String loginStudentNumber = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity loginUser = userRepository.findByStudentnumber(loginStudentNumber);

        contentEntity.setUserEntity(loginUser);
        
        contentRepository.save(contentEntity);

        return true;
    }
}
