package com.model;

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
public class Complain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String reason;
    private Float money;
    private String from;
    private String to;
    private Integer fromid;
    private Integer toid;
    private Integer prjid;
    private Integer type;
    private Integer state = 0;
    private String name;
    private int expert;
}
