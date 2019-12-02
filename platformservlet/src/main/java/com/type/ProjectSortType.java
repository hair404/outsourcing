package com.type;

import lombok.Getter;

public enum ProjectSortType {

    UNKNOWN(-1),
    DEFAULT(0),
    PRICE(1),
    PAY_IN_ADVANCED(2);

    @Getter
    private int id;

    ProjectSortType(int id) {
        this.id = id;
    }

    public static ProjectSortType fromId(int id) {
        for (ProjectSortType type : ProjectSortType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return ProjectSortType.UNKNOWN;
    }
}
