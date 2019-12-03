package com.type;

import lombok.Getter;

public enum RefundType {

    UNKNOWN(-1),
    RUNNING(0),
    CANCELED(1);

    @Getter
    private int id;

    RefundType(int id){
        this.id = id;
    }

    public static RefundType fromId(int id){
        for (RefundType type : RefundType.values()){
            if (type.id == id){
                return type;
            }
        }
        return RefundType.UNKNOWN;
    }
}
