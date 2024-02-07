package com.jbnucpu.www.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserInfoEntity {
    @EmbeddedId
    private UserInfoId id;

    private String knowledge;
    private String availableTime;
    private String studyGoal;
}