package com.practice.facility.auth.repository;

import com.practice.facility.auth.model.RoleEntity;
import com.practice.facility.auth.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName roleUser);
}
