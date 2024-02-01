package com.jbnucpu.www.controller.study;

import com.jbnucpu.www.dto.StudyDTO;
import com.jbnucpu.www.service.StudyService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudyController {

    private final StudyService studyService;

    public StudyController(StudyService studyService){
        this.studyService = studyService;
    }


    // 스터디 메인 (스터디 목록 보여줌)
    @GetMapping("/study")
    public String studyList(Model model){
        model.addAttribute("studyEntityList_data",studyService.getList());
        return "studylist";
    }

    // 개별 상세 스터디 페이지 조회
    @GetMapping("/study/{no}")
    public String studyNo(@PathVariable("no")Long no, Model model){

        model.addAttribute("studyEntity_data", studyService.findStudy(no));
        model.addAttribute("enrollEntities_data", studyService.findEnrollEntities(no));

        return "studyno";
    }

    // 스터디 개설
    @GetMapping("/study/open")
    public String studyOpen(){

        return "studyopen";
    }

    //BindingResult는 Model에 자동으로 포함되기 때문에 Model 객체를 파라미터에 추가하지 않았dma.
    @PostMapping("/study/open")
    public String processStudyOpen(@Valid StudyDTO studyDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage","실패: 모든 값을 입력해주세요");
            model.addAttribute("studyDTO", studyDTO);
            return "studyopen"; // 오류가 발생했을 때 보여줄 뷰 이름
        }
        Boolean successfullyOpenStudyFromLoginedUser = studyService.processOpenStudy(studyDTO);
        if(!successfullyOpenStudyFromLoginedUser){
            model.addAttribute("errorMessage","실패: 로그인 한 유저만 스터디 개설 가능");
            model.addAttribute("studyDTO", studyDTO);
            return "studyopen"; // 오류가 발생했을 때 보여줄 뷰 이름
        }

        return "redirect:/study";
    }

    // 스터디 수정
    @GetMapping("/study/edit/{no}")
    public String studyUpdateForm(@PathVariable("no")Long no, Model model){
        model.addAttribute("studyEntity_data", studyService.findStudy(no));
        return "studyedit";
    }

    @PostMapping("/study/edit/{no}")
    public String studyUpdate(@PathVariable("no")Long no,@Valid StudyDTO studyDTO){
        studyService.editStudy(studyDTO,no);//studyDTO로 no번째 엔티티를 수정
        return "redirect:/study/{no}";
    }

    //스터디 삭제
    @GetMapping("/study/delete/{no}")
    public String studyDelete(@PathVariable("no")Long no){
        studyService.deleteStudy(no);
        return "redirect:/study";
    }

    // 스터디 신청
    @PostMapping("/study/apply/{no}")
    public String studyApply(@PathVariable("no")Long no){
        studyService.applyStudy(no);

        return "redirect:/study/{no}";
    }

    // 스터디 삭제
    @PostMapping("/study/quit/{no}")
    public String studyQuit(){
        studyService.quitStudy();
        return "redirect:/study/{no}";
    }

}
