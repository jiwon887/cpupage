package com.jbnucpu.www.controller.board;

import com.jbnucpu.www.dto.ArticleDTO;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.repository.NoticeRepository;
import com.jbnucpu.www.service.AuthService;
import com.jbnucpu.www.service.DeleteService;
import com.jbnucpu.www.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor // final이 붙은 속성을 포함하는 생성자 자동 생성
@Controller
public class NoticeController {

    private final NoticeRepository noticeRepository;

    private final ReadService readService;

    private final DeleteService deleteService;

    private final AuthService authService;

    @GetMapping("/notice")
    public String notice(Model model, @PageableDefault(size = 3, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<NoticeEntity> paging = this.readService.processNoticePageListRead(pageable);

        // 페이지 번호를 표시할 범위 계산
        int currentPageIndex = pageable.getPageNumber();
        int totalPages = paging.getTotalPages();
        int range = 2; // 표시할 페이지 범위 조절 (현재 페이지를 중심으로 양 옆으로 표시)
        int startPageIndex = Math.max(0, currentPageIndex - range);
        int endPageIndex = Math.min(totalPages - 1, currentPageIndex + range);
        int[] pageIndexArray = IntStream.rangeClosed(startPageIndex, endPageIndex).toArray();
        
        // 게시물 번호 구하기
        List<Long> postNumbers = new ArrayList<>();
        for (int i = 0; i < pageable.getPageSize(); i++) {
            Long postNumber = paging.getTotalElements() - (pageable.getPageNumber() * pageable.getPageSize()) - i;
            postNumbers.add(postNumber);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("pageIndexArray", pageIndexArray);
        model.addAttribute("prev", pageable.previousOrFirst().getPageNumber()); //이전 페이지의 페이지 번호
        model.addAttribute("next", pageable.next().getPageNumber()); // 다음 페이지의 페이지 번호
        model.addAttribute("hasNext", paging.hasNext()); // 더이상 보여줄 페이지가 없음에도 페이지가 넘어가는 것을 막기 위한 bool
        model.addAttribute("hasPrev", paging.hasPrevious()); // 더이상 보여줄 페이지가 없음에도 페이지가 넘어가는 것을 막기 위한 bool
        model.addAttribute("postNumbers", postNumbers);
        return "notice";
    }

//    @GetMapping("/notice")
//    public String notice(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
//        Page<NoticeEntity> paging = this.readService.processNoticePageListRead(page);
//        model.addAttribute("paging", paging);
//        return "notice";
//    }

//    @GetMapping("/notice")
//    public String notice(Model model) {
//        List<NoticeEntity> noticeEntityList = readService.processNoticeListRead();
//        model.addAttribute("noticeList", noticeEntityList);
//        return "notice";
//    }

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
