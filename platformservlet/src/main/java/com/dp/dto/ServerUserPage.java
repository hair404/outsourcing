package com.dp.dto;

import com.alibaba.fastjson.JSONObject;
import com.model.Project;
import com.model.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ServerUserPage {
    private long total;
    private List<JSONObject> data;

    public ServerUserPage(long total, List<JSONObject> data) {
        this.total = total;
        this.data = data;
    }
}
