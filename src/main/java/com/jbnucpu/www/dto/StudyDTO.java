package com.jbnucpu.www.dto;

import com.jbnucpu.www.entity.StudyEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyDTO {

    private boolean accept;

    @NotNull
    @NotEmpty
    private String studyType;//BASIC(기본),SELF(자율)

    @NotNull // 클라이언트에서 Null값 보내면 400에러 발생시킴
    @NotEmpty
    private String title;

    private String studentNumber; //

    @NotNull
    @NotEmpty
    private String studySubject;

    @NotNull
    @NotEmpty
    private String studyDetail;

    public StudyEntity toEntity(){
        StudyEntity studyEntity = new StudyEntity();

        studyEntity.setTitle(title);
        studyEntity.setStudentNumber(studentNumber);
        studyEntity.setStudyType(studyType);
        studyEntity.setStudySubject(studySubject);
        studyEntity.setStudyDetail(studyDetail);

        return studyEntity;
    }
}
