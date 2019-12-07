package com.dp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dp.dto.ServerNotification;
import com.model.Notification;
import com.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationDataProcessor {

    @Resource
    private UserService userService;

    public List<ServerNotification> getNotifications(int userId) {
        List<ServerNotification> notifications = new ArrayList<>();
        userService.getNotifications(userId).forEach(it -> {
            notifications.add(new ServerNotification(
                    it.getTitle(),
                    it.getContent(),
                    it.getTime(),
                    getUrl(it)
            ));
        });
        return notifications;
    }

    private String getUrl(Notification notification) {
        JSONObject json = JSON.parseObject(notification.getParams());
        switch (notification.getAction()) {
            case JUMP_PROJECT:
                return "detail/" + json.getString("solrId");
            case JUMP_USER:
                return "display/" + json.getString("id");
            case CENTER:
                return "center";
        }
        return "";
    }
}
