package com.type;

public enum PayType {

    UNKNOWN(0),
    DEPOSIT(1), //定金
    PAY_IN_ADVANCED(2), //首付金
    STEP(3); //进度款

    private int id;

    PayType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PayType fromId(int id) {
        for (PayType type : PayType.values()) {
            if (type.id == id) {
                return type;
            }
        }
        return PayType.UNKNOWN;
    }
}