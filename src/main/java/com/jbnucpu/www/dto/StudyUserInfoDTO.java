package com.jbnucpu.www.dto;

import com.jbnucpu.www.entity.StudyUserInfoEntity;
import com.jbnucpu.www.entity.StudyUserInfoId;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StudyUserInfoDTO {
    private StudyUserInfoId id;
    private String knowledge;
    private String availableTime;
    private String studyGoal;
}
