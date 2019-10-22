package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "reason")

public class Reason {
	@Id
	private Integer id;
	private Integer studioid;
	private String reason;
	private Integer project_id;
}
