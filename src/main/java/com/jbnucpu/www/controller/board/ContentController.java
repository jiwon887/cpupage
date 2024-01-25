package com.jbnucpu.www.controller.board;

import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.repository.ContentRepository;
import com.jbnucpu.www.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ContentController {

    private final ContentRepository contentRepository;

    private final ReadService readService;

    @GetMapping("/content")
    public String content(Model model){
        List<ContentEntity> contentEntityList = this.contentRepository.findAll();
        model.addAttribute("contentList", contentEntityList);
        return "content";
    }

    @GetMapping("/content/{id}")
    public String noticeDetail(Model model, @PathVariable("id") Long id) {
        ContentEntity content = this.readService.processContentRead(id);
        model.addAttribute("content", content);
        return "content_detail";
    }
}
