package com.type;

import lombok.Getter;

public enum UserValidState {
    UNKNOWN(-1),
    WAiTING_UPLOAD(0),
    PENDING(2),
    REJECT(3),
    PASS(1);

    @Getter
    private int id;

    UserValidState(int id) {
        this.id = id;
    }

    public static UserValidState fromId(int id) {
        for (UserValidState state : UserValidState.values()) {
            if (state.id == id) {
                return state;
            }
        }
        return UserValidState.UNKNOWN;
    }
}
