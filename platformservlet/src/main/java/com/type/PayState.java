package com.type;

public enum PayState {

    UNKNOWN(0),
    WAIT(1),
    FAILED(2),
    DONE(3);

    private int id;

    PayState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PayState fromId(int id) {
        for (PayState type : PayState.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return PayState.UNKNOWN;
    }
}
