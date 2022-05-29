package com.practice.facility.facilities.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "facility")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //제목
    @NotBlank
    @Column(length = 200)
    private String title;

    //상세내용
    @Lob
    @Column(name = "contents", columnDefinition = "CLOB")
    private String contents;

    //이용가능여부
    @NotNull
    private Boolean available;

    //운영기간
    @Column(length = 8)
    private String operatingStartDate;
    @Column(length = 8)
    private String operatingEndDate;

    //접수기간
    @Column(length = 8)
    private String receiptStartDate;
    @Column(length = 8)
    private String receiptEndDate;

    //구역
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "facility_areas",
        joinColumns = @JoinColumn(name = "facility_id"),
        inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private Set<AreaEntity> areas = new HashSet<>();

    //시간
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "facility")
    private List<Time> times = new ArrayList<>();


}
