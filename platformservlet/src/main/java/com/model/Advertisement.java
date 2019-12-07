package com.model;

import javax.persistence.*;
import javax.transaction.Transactional;

import com.type.AdState;
import com.type.AdType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Advertisement{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int price;
    private int type;
    private int typeId;
    private int state;

    @Transient
    public AdState getState() {
        return AdState.fromId(state);
    }

    @Transient
    public void setState(AdState adState) {
        this.state = adState.getId();
    }

    @Transient
    public AdType getType(){
        return AdType.fromId(type);
    }

    @Transient
    public void setType(AdType adType){
        this.type = adType.getId();
    }
}
