package com.type;

import lombok.Getter;

public enum AdType {
    UNKNOWN(0),
    PROJECT(1),
    STUDIO(2);

    @Getter
    private int id;

    AdType(int id) {
        this.id = id;
    }

    public static AdType fromId(int id) {
        for (AdType type : AdType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return AdType.UNKNOWN;
    }
}
