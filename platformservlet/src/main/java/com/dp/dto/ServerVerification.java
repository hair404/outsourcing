package com.dp.dto;

import lombok.Getter;

@Getter
public class ServerVerification {

    private int id;
    private int type;
    private int state;
    private String username;
    private String img;
    private int userId;

    public ServerVerification(int id, int type, int state, String username, String img, int userId) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.username = username;
        this.img = img;
        this.userId = userId;
    }
}
