package com.jbnucpu.www.controller.board;

import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.repository.NoticeRepository;
import com.jbnucpu.www.service.ReadService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자 자동 생성
@Controller
public class NoticeController {

    private final NoticeRepository noticeRepository;

    private final ReadService readService;

    @GetMapping("/notice")
    public String notice(Model model) {
        List<NoticeEntity> noticeEntityList = this.noticeRepository.findAll();
        model.addAttribute("noticeList", noticeEntityList);
        return "notice";
    }

    @GetMapping("/notice/{id}")
    public String noticeDetail(Model model, @PathVariable("id") Long id) {
        NoticeEntity notice = this.readService.processNoticeRead(id);
        model.addAttribute("notice", notice);
        return "notice_detail";
    }
}
