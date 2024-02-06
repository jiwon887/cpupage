package com.jbnucpu.www.repository;

import com.jbnucpu.www.entity.StudyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudyRepository extends JpaRepository<StudyEntity, Long> {
    @Query("SELECT s FROM StudyEntity s WHERE s.accept = true AND s.studyType = 'BASIC'")
    List<StudyEntity> findAllAcceptedBasicStudies();

    @Query("SELECT s FROM StudyEntity s WHERE s.accept = false AND s.studyType = 'BASIC'")
    List<StudyEntity> findAllUnacceptedBasicStudies();

    @Query("SELECT s FROM StudyEntity s WHERE s.accept = true AND s.studyType = 'SELF'")
    List<StudyEntity> findAllAcceptedSelfStudies();

    @Query("SELECT s FROM StudyEntity s WHERE s.accept = false AND s.studyType = 'SELF'")
    List<StudyEntity> findAllUnacceptedSelfStudies();
}
