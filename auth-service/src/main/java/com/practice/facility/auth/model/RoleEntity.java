package com.practice.facility.auth.model;

import lombok.Getter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;

    public RoleEntity() {
    }

    public RoleEntity(RoleName name) {
        this.name = name;
    }
}
