package com.type;

import lombok.Getter;

public enum VerificationType {

    UNKNOWN(-1),
    STUDIO(1),
    COMPANY(2),
    STUDENT(3);

    @Getter
    private int id;

    VerificationType(int id) {
        this.id = id;
    }

    public static VerificationType fromId(int id) {
        for (VerificationType state : VerificationType.values()) {
            if (state.id == id) {
                return state;
            }
        }
        return VerificationType.UNKNOWN;
    }
}
