package com.jbnucpu.www.repository;

import com.jbnucpu.www.entity.StudyEnrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyEnrollRepository extends JpaRepository<StudyEnrollEntity, Long> {

    Optional<StudyEnrollEntity> findByUserEntityId(Long userId);
}
