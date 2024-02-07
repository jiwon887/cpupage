package com.jbnucpu.www.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter @Setter
public class UserInfoId implements Serializable {
    private Long studentId;
    private Long studyId;

    public UserInfoId() {}
}
