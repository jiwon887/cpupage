package com.jbnucpu.www.repository;

import com.jbnucpu.www.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByStudentnumber(String studentnumber);

    UserEntity findByStudentnumber(String studentnumber);

    UserEntity findByName(String name);
}
