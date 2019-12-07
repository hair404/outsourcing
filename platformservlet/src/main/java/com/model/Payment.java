package com.model;

import com.type.PayState;
import com.type.PayType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //付款类型
    private int type;

    //付款数量
    private double amount;

    //关联 PayType 枚举类
    private String orderId;

    //付款状态
    private int state;

    //定金-项目id 首付款-项目id 进度款-步骤id
    private int typeId;

    private Date time = new Date();

    @Transient
    public PayState getState() {
        return PayState.fromId(state);
    }

    @Transient
    public void setState(PayState state) {
        this.state = state.getId();
    }

    @Transient
    public void setType(PayType payType) {
        this.type = payType.getId();
    }

    @Transient
    public PayType getType() {
        return PayType.fromId(type);
    }
}
