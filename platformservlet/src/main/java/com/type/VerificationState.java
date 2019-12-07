package com.type;

import lombok.Getter;

public enum VerificationState {

    UNKNOWN(-1),
    PENDING(2), //等待审核
    REJECT(3), //拒绝
    CANCEL(4), //取消
    PASS(1); //通过

    @Getter
    private int id;

    VerificationState(int id) {
        this.id = id;
    }

    public static VerificationState fromId(int id) {
        for (VerificationState state : VerificationState.values()) {
            if (state.id == id) {
                return state;
            }
        }
        return VerificationState.UNKNOWN;
    }
}
