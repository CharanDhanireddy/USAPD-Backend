package com.usapd.backend.entity;

import javax.persistence.*;

@Entity
@Table(name="Test", schema="vdhavaleswarapu", catalog="vdhavaleswarapu")
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Integer id_number;
    public String username;
}
