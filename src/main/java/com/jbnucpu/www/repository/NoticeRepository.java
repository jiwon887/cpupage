package com.jbnucpu.www.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.jbnucpu.www.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {
    List<NoticeEntity> findByUserEntityId(Long userId);
    Page<NoticeEntity> findAll(Pageable pageable);
}
