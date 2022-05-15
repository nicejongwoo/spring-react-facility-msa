package com.practice.facility.facilities.enumcode;

import lombok.Getter;

@Getter
public enum AreaName {

    A_AREA("A_AREA", "A구역"),
    B_AREA("B_AREA", "B구역"),
    C_AREA("C_AREA", "C구역"),
    D_AREA("D_AREA", "D구역")
    ;

    private String value;
    private String name;

    AreaName(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
