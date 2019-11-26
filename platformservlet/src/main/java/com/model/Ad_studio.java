package com.model;

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
@Table(name = "ad_studio")
public class Ad_studio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String tag;
	private String img;
	private String avatar;
	private String email;
	private String tel;
	private float ad_price;
	private float weight;
	private Integer account_id;
	private Float credit;
	@Column(name = "solr_id")
	private String solrid;
	private Integer state;
}
