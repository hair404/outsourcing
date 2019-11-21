package com.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Entity
@Table(name = "bid")
public class Bid {
  @Id
  private Integer id;
  private Integer project_id;
  private Integer studio_id;
  private Integer state;
  private Integer company_id;
  private Integer quote;
  }
