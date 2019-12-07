package com.dp.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ServerAdvertisement {

    private int id;
    private String belong;
    private int type;
    private int money;
    @JsonProperty("solr_id")
    private String solrId;
    private int state;

    public ServerAdvertisement(int id, String belong, int type, int money, String solrId, int state) {
        this.id = id;
        this.belong = belong;
        this.type = type;
        this.money = money;
        this.solrId = solrId;
        this.state = state;
    }
}
