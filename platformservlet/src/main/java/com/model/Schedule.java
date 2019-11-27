package com.model;

import java.io.Serializable;

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
@Table(name = "schedule")
public class Schedule implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private Integer id;
	private Integer studio_id;
	private Integer prj_id;
	private Integer current;
	private Integer company_id;
	private Integer total;
	
}
