package com.service;

import com.dao.NotificationRepository;
import com.model.Notification;
import com.type.ActionType;
import com.utils.GoEasyNotification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotificationService {

    @Resource
    private NotificationRepository notificationRepository;
    /**
     * 发布通知
     *
     * @param userId  用户id
     * @param title   标题
     * @param content 内容
     * @param action  操作
     * @param params  参数
     */
    public void notify(int userId, String title, String content, ActionType action, String params) {
        Notification notification = new Notification();
        notification.setUid(userId);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setAction(action);
        notification.setParams(params);
        GoEasyNotification.notify(userId + "", title, content);
        notificationRepository.save(notification);
    }
}
