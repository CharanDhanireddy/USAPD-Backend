package com.usapd.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="USERS", schema="VDHAVALESWARAPU", catalog="VDHAVALESWARAPU")
public class Users {
    @Id
    @Column(name="USER_NAME")
    public String USER_NAME;

    @Column(name="EMAIL_ID")
    public String EMAIL_ID;

    @Column(name="STATE_CODE")
    public Integer STATE_CODE;

    @Override
    public String toString(){
        return "Username: " + USER_NAME + ", Email_ID: " + EMAIL_ID;
    }
}


