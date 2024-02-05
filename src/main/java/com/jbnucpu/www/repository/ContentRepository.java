package com.jbnucpu.www.repository;

import com.jbnucpu.www.entity.ContentEntity;
import com.jbnucpu.www.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<ContentEntity, Long> {
    List<ContentEntity> findByUserEntityId(Long userId);
    Page<ContentEntity> findAll(Pageable pageable);

}
