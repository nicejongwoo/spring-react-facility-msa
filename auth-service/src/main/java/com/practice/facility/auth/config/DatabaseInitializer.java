package com.practice.facility.auth.config;

import com.practice.facility.auth.model.RoleEntity;
import com.practice.facility.auth.model.RoleName;
import com.practice.facility.auth.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class DatabaseInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        RoleEntity userRole = new RoleEntity(RoleName.ROLE_USER);
        RoleEntity adminRole = new RoleEntity(RoleName.ROLE_ADMIN);

        roleRepository.save(userRole);
        roleRepository.save(adminRole);

    }

    @PreDestroy
    public void destroy() {
        roleRepository.deleteAll();
    }

}
