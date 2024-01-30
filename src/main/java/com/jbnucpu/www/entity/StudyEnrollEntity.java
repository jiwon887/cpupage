package com.jbnucpu.www.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StudyEnrollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudyEntity studyEntity;

    @ManyToOne
    private UserEntity userEntity;

}
