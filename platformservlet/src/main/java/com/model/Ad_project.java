package com.model;

import java.sql.Date;

import javax.persistence.Column;
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
@Table(name = "ad_project")
public class Ad_project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date dueTime;
	private float weight;
	private String prjname;
	private Integer tag;
	private Integer subtag;
	private float price;
	private float ad_price;
	private String img;
	@Column(name="solr_id")
	private String solrid;
	private Integer prj_id;
	private Integer state;
}
