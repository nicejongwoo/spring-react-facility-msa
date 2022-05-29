package com.practice.facility.facilities.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "areas")
public class AreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String code;

    @Column(nullable = false)
    private boolean available;

    public AreaEntity(String name, String code, boolean available) {
        this.name = name;
        this.code = code;
        this.available = available;
    }

    public AreaEntity updateNameAndCode(String name, String code) {
        this.name = name;
        this.code = code;
        return this;
    }

    public void disable() {
        this.available = false;
    }
}
