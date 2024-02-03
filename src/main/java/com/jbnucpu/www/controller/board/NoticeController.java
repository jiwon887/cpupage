package com.jbnucpu.www.controller.board;

import com.jbnucpu.www.dto.ArticleDTO;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.repository.NoticeRepository;
import com.jbnucpu.www.service.AuthService;
import com.jbnucpu.www.service.DeleteService;
import com.jbnucpu.www.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.format.DecimalStyle;
import java.util.List;

@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자 자동 생성
@Controller
public class NoticeController {

    private final NoticeRepository noticeRepository;

    private final ReadService readService;

    private final DeleteService deleteService;

    private final AuthService authService;

    @GetMapping("/notice")
    public String notice(Model model) {
        List<NoticeEntity> noticeEntityList = readService.processNoticeListRead();
        model.addAttribute("noticeList", noticeEntityList);
        return "notice";
    }

    @GetMapping("/notice/{id}")
    public String noticeDetail(Model model, @PathVariable("id") Long id) {
        NoticeEntity notice = this.readService.processNoticeRead(id);
        model.addAttribute("notice", notice);
        return "notice_detail";
    }

    @GetMapping("/notice/update/{id}")
    public String updateNotice(Model model, @PathVariable("id") Long id) {
        
        if(!authService.isAuthenticated()){
            System.out.println("수정 실패: 로그인 한 사용자만 수정 가능");
            return "redirect:/notice";
        }

        NoticeEntity notice = this.readService.processNoticeRead(id);

        if(!authService.getUsername().equals(notice.getUserEntity().getStudentnumber())){
            System.out.println("수정 실패: 작성자만 수정 가능");
            return "redirect:/notice";
        }

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(notice.getTitle());
        articleDTO.setContent(notice.getContent());

        model.addAttribute("post", articleDTO);
        model.addAttribute("id", id);
        model.addAttribute("type", "notice");

        return "update_editor";
    }

    @PostMapping("/notice/update/{id}")
    public String processUpdateNotice(ArticleDTO articleDTO, @PathVariable("id") Long id){

        NoticeEntity notice = this.readService.processNoticeRead(id);
        notice.setTitle(articleDTO.getTitle());
        notice.setContent(articleDTO.getContent());

        noticeRepository.save(notice);

        return "redirect:/notice";
    }

    @GetMapping("/notice/delete/{id}")
    public String processDeleteNotice(@PathVariable("id") Long id){

        deleteService.processNoticeDelete(id);

        return "redirect:/notice";
    }
}
