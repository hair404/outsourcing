package com.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account implements Serializable{
	
	private static final long serialVersionUID = 4002307660376310109L;
	@Id
	private Integer id;
	private String tel;
	private String password;
	}
