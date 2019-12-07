package com.model;

import com.type.UserType;
import com.type.VerificationState;
import com.type.VerificationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String image;

    private int state;

    private int userId;

    private int type;

    @Transient
    public VerificationType getType() {
        return VerificationType.fromId(type);
    }

    @Transient
    public void setType(VerificationType type) {
        this.type = type.getId();
    }

    @Transient
    public VerificationState getState() {
        return VerificationState.fromId(state);
    }

    @Transient
    public void setState(VerificationState verificationState) {
        this.state = verificationState.getId();
    }


}
