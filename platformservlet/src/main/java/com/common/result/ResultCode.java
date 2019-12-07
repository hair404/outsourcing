package com.common.result;

import lombok.Getter;

public enum ResultCode {
    OK(1, "OK"),
    UNKNOWN(0, "未知错误"),
    WRONG_ROLE(-1,"角色错误"),
    NOT_FOUND_USER(-2,"用户不存在");

    @Getter
    private int id;

    @Getter
    private String description;

    ResultCode(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
