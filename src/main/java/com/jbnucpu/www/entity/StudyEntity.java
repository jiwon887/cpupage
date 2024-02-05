package com.jbnucpu.www.entity;

import com.jbnucpu.www.dto.StudyDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class StudyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean accept;

    private String studyType;//BASIC(기본),SELF(자율)

    private String title;

    private String studentNumber;

    private String studySubject;

    private String studyDetail;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;

    @OneToMany(mappedBy = "studyEntity",cascade = CascadeType.ALL)
    private Set<StudyEnrollEntity> studyEnrollEntities = new HashSet<StudyEnrollEntity>();

}
