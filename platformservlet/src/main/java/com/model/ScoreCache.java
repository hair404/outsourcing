package com.model;

import com.type.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "score_cache")
public class ScoreCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    //公司 1 工作室 2
    private int type;

    private double score;

    @Transient
    public UserType getType() {
        return UserType.fromId(type);
    }
}
