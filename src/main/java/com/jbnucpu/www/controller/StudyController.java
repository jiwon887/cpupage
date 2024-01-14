package com.jbnucpu.www.controller;

import com.jbnucpu.www.dto.StudyDTO;
import com.jbnucpu.www.service.StudyOpenService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudyController {

    private final StudyOpenService studyOpenService;

    public StudyController(StudyOpenService studyOpenService){
        this.studyOpenService = studyOpenService;
    }

    
    // 스터디 메인 (스터디 목록 보여줌)
    @GetMapping("/study")
    public String studyList(){

        return "studylist";
    }

    // 스터디 개설
    @GetMapping("/study/open")
    public String studyOpen(){

        return "studyopen";
    }

    @PostMapping("/study/open")
    public String processStudyOpen(@Valid StudyDTO studyDTO){ //@Valid 어노테이션으로 null 값 들어오면 에러 발생 시키기

        Boolean isSigned = studyOpenService.processOpenStudy(studyDTO);

        return "studylist";
    }

    // 스터디 수정
    @GetMapping("/study/open/{no}")
    public String studyUpdate(){

        return "studyopen";
    }
    
    // 스터디 삭제
    //    @GetMapping("/study/delete/{no}")
    //    public String studyDelete(){
    //
    //        return "studylist";
    //    }
}
