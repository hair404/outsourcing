package com.model;


import javax.persistence.Entity;
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
	private Integer id;
	private String filename;
	private String url;
	private Integer user_id;
	private Integer project_id;
	
}
