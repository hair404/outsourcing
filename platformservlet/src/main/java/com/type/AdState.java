package com.type;

import lombok.Getter;

public enum AdState {

    UNKNOWN(-1),
    PADDING(0),
    PASS(1),
    REJECT(2);

    @Getter
    private int id;

    AdState(int id) {
        this.id = id;
    }

    public static AdState fromId(int id) {
        for (AdState state : AdState.values()) {
            if (state.getId() == id) {
                return state;
            }
        }
        return AdState.UNKNOWN;
    }

}
