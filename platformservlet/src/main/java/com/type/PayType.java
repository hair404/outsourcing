package com.type;

import lombok.Getter;

@Getter
public enum PayType {
    //PayTypeId TypeId
    UNKNOWN(0, false),
    DEPOSIT_TO_STUDIO(1, true), //给工作室的定金
    PAY_IN_ADVANCED(2, true), //首付金
    STEP(3, true),//进度款
    DEPOSIT_TO_COMPANY(4, true), // 给公司定金
    AD_STUDIO(5, true),//工作室的广告款
    REFUND(6, false); //公司广告款 - PaymentId

    private int id;
    private boolean income;

    PayType(int id, boolean income) {
        this.id = id;
        this.income = income;
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