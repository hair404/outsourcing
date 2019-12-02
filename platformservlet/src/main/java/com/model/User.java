package com.model;

import javax.persistence.*;

import com.type.UserType;
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

    @Transient
    public UserType getType() {
        return UserType.fromId(type);
    }


    @Transient
    public void setType(UserType type) {
        this.type = type.getId();
    }
}
