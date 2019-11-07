package com.model;

import javax.persistence.Entity;
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
	private String entity;
}
