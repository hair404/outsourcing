package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "complain_reason")

public class Complain_reason {
	@Id
	private Integer id;
	private Integer entity;//1 for company,2 for studio
	private String reason;
	private Integer project_id;
}
