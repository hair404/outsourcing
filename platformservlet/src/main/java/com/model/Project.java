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
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String solr_id;
	private String prjname;
	private Integer tag;
	private Integer subtag;
	private String img;
	private Date releaseTime;
	private Date deadline;
	private String info;
	private Integer state;
	private Integer ifAd;
	private Float price;
	private Integer companyID;
	private String companyName;
	private Integer studioID;
	private Integer hasPaid;
	private Integer ispia;
	private Integer isdeposit;
	private Float payinadvance;
	private Integer isform;
	private Integer issetprice;
	private Integer totalPart;
	private Integer current;
	private Integer isconfirm;
	private Date startTime;
	private Date payDeadline;//付惩罚金的最迟时间
	private Float companyRate;
	private Date countdown;
	private Float studioRate;
	private String entity;
	
}
