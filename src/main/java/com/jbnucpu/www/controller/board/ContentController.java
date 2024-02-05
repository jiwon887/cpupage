package com.jbnucpu.www.controller.board;

import com.jbnucpu.www.dto.ArticleDTO;
import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.repository.ContentRepository;
import com.jbnucpu.www.service.AuthService;
import com.jbnucpu.www.service.DeleteService;
import com.jbnucpu.www.service.ReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
public class ContentController {

    private final ContentRepository contentRepository;

    private final ReadService readService;

    private final DeleteService deleteService;

    private final AuthService authService;

    @GetMapping("/content")
    public String content(Model model, @PageableDefault(size = 3, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ContentEntity> paging = this.readService.processContentPageListRead(pageable);

        // 표시할 페이지 번호 범위 계산
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

        if(!authService.getUsername().equals(content.getUserEntity().getStudentnumber())){
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
