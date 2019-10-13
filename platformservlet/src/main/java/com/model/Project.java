package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "project")
public class Project {
		@Id
		private Integer uuid;
		private String id;
		private String  prjname;
		private Integer tag;
	    private Integer sub_tag;
		private String img;
		private String releaseTime;
		private String info;
		private String state;
		private Integer ifAd;
		private String deadline;
		private float price;
		private Integer companyId;
		private String studioId;
	}

