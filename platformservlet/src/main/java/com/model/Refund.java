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
@Table(name = "refund")
public class Refund {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer project_id;
	private Integer studio_id;
	private Integer company_id;
    private Integer type;//公司对工作室0，工作室对公司1
    private String reason;
    private Float money;
    private Integer state;//申诉中 0 ，申诉成功1，申诉失败2
}
