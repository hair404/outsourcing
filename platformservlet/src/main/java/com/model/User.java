package com.model;

import javax.persistence.*;

import com.type.UserType;
import com.type.UserValidState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    private Integer id; //primary key
    private String solr_id; // be used in solr
    private Integer account_id; //foreign key
    private String username;
    private String name;
    private String tel;
    private String info;
    private String alipay;
    private String email;
    private Integer type;
    private String img;
    private String avatar;
    private Float credit;
    private String entity;
    private Integer isValid;
    private String token;
    @Column(name = "is_student")
    private boolean student;

    //是否被删除
    private boolean deleted = false;

    @Transient
    public UserType getType() {
        return UserType.fromId(type);
    }


    @Transient
    public void setType(UserType type) {
        this.type = type.getId();
    }

    @Transient
    public UserValidState getValidState() {
        return UserValidState.fromId(isValid);
    }

    @Transient
    public void setValidState(UserValidState userValidState) {
        this.isValid = userValidState.getId();
    }
}
