package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer uuid;
	private Integer user_id;
	private String id;
	private String username;
	private String name;
	private String tel;
	private String info;
	private String alipay;
	private String email;
	private Integer type;
	private String img;
	private String entity;
}
