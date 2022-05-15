package com.practice.facility.facilities.model;

import com.practice.facility.facilities.enumcode.AreaName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private AreaName name;

    public Area(AreaName name) {
        this.name = name;
    }
}
