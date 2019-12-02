package com.type;

import lombok.Getter;

public enum SearchType {

    UNKNOWN(-1),
    PROJECT(0),
    STUDIO(1);

    @Getter
    private int id;

    SearchType(int id) {
        this.id = id;
    }

    public static SearchType fromId(int id) {
        for (SearchType type : SearchType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return SearchType.UNKNOWN;
    }
}
