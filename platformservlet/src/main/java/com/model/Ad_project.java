package com.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "ad_project")
public class Ad_project {
	@Id
	private Integer id;
	private Date dueTime;
	private float weight;
	private String prjname;
	private Integer tag;
	private Integer subTag;
	private String price;
	private float ad_price;
	private String img;
	private String prj_id;
}
