package com.jbnucpu.www.repository;

import com.jbnucpu.www.entity.NoticeEntity;
import com.jbnucpu.www.entity.StudyEnrollEntity;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByUserEntityId(Long userId);
}
