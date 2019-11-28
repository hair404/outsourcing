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
  private Integer projectId;
  private Integer studioId;
  private Integer state;
  private Integer companyId;
  private Integer quote;
}
