package com.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "ad")
public class Ad {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private Date dueTime;
	private float weight;
	private String prjName;
	private Integer tag;
	private Integer subTag;
	private float price;
	private float ad_price;
	private String img;
	private String info;
	private String tel;
	private String email;
}
