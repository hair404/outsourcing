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
		private Integer id;
		private String solr_id;
		private String  prjname;
		private Integer tag;
	    private Integer sub_tag;
		private String img;
		private String releaseTime;
		private String deadline;
		private String info;
		private Integer state;
		private Integer ifAd;
		private float price;
		private Integer companyID;
		private Integer studio_id;
	}

