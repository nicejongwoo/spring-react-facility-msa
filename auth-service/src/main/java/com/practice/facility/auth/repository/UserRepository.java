package com.practice.facility.auth.repository;

import com.practice.facility.auth.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
