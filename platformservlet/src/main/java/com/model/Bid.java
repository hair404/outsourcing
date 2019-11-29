package com.model;

import javax.persistence.*;

import com.type.BidState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer projectId;
    private Integer studioId;
    //0 等待 1 未中标 2 中标
    private Integer state;
    private Integer companyId;
    private Integer quote;

    @Transient
    public BidState getState() {
        return BidState.fromId(state);
    }

    @Transient
    public void setState(BidState state) {
        this.state = state.getId();
    }
}
