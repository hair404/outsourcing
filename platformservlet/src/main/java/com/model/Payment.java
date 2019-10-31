package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
	@Id
	private Integer id;
	private Integer state;
    private Integer project_id;
    private Integer company_id;
    private Integer studio_id;
}
