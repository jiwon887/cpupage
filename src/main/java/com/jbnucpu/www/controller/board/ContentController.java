package com.jbnucpu.www.controller.board;

import com.jbnucpu.www.dto.ArticleDTO;
import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.repository.ContentRepository;
import com.jbnucpu.www.service.AuthService;
import com.jbnucpu.www.service.DeleteService;
import com.jbnucpu.www.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ContentController {

    private final ContentRepository contentRepository;

    private final ReadService readService;

    private final DeleteService deleteService;

    private final AuthService authService;

    @GetMapping("/content")
    public String content(Model model){
        List<ContentEntity> contentEntityList = this.contentRepository.findAll();
        model.addAttribute("contentList", contentEntityList);
        return "content";
    }

    @GetMapping("/content/{id}")
    public String contentDetail(Model model, @PathVariable("id") Long id) {
        ContentEntity content = this.readService.processContentRead(id);
        model.addAttribute("content", content);
        return "content_detail";
    }

    @GetMapping("/content/update/{id}")
    public String updateContent(Model model, @PathVariable("id") Long id) {

        if(!authService.isAuthenticated()){
            System.out.println("수정 실패: 로그인 한 사용자만 수정 가능");
            return "redirect:/content";
        }

        ContentEntity content = this.readService.processContentRead(id);

        if(!authService.getUsername().equals(content.getStudentNumber())){
            System.out.println("수정 실패: 작성자만 수정 가능");
            return "redirect:/content";
        }

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(content.getTitle());
        articleDTO.setContent(content.getContent());

        model.addAttribute("post", articleDTO);
        model.addAttribute("id", id);
        model.addAttribute("type", "content");


        return "update_editor";
    }

    @PostMapping("/content/update/{id}")
    public String processUpdateContent(ArticleDTO articleDTO, @PathVariable("id") Long id){

        ContentEntity content = this.readService.processContentRead(id);
        content.setTitle(articleDTO.getTitle());
        content.setContent(articleDTO.getContent());

        contentRepository.save(content);

        return "redirect:/content";
    }

    @GetMapping("/content/delete/{id}")
    public String processDeleteContent(@PathVariable("id") Long id){

        deleteService.processContentDelete(id);

        return "redirect:/content";
    }
}
