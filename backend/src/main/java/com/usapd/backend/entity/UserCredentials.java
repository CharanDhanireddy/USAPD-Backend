package com.usapd.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERCREDENTIALS", schema="VDHAVALESWARAPU", catalog="VDHAVALESWARAPU")
public class UserCredentials {
    @Id
    public String EMAIL_ID;
    public String PASSWORD;

    @Override
    public String toString(){
        return EMAIL_ID;
    }
}

