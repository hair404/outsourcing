package com.type;

import lombok.Getter;

public enum BidState {

    UNKNOWN(-1),
    WAIT(0),
    REJECT(1),
    WIN(2);

    @Getter
    private int id;

    BidState(int id) {
        this.id = id;
    }

    public static BidState fromId(int id) {
        for (BidState type : BidState.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return BidState.UNKNOWN;
    }
}
