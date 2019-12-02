package com.model;

import com.type.ActionType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int uid;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    //转跳方式
    private int action;

    //转跳参数
    private String params;

    private Date time = new Date();

    public ActionType getAction() {
        return ActionType.fromId(action);
    }

    public void setAction(ActionType type) {
        this.action = type.getId();
    }
}
