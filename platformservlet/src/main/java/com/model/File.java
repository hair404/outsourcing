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
@Table(name = "file")
public class File {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String filename;
	private String url;
	private Integer user_id;
	private Integer project_id;
	
}
