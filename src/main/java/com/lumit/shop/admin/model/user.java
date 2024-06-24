package com.lumit.shop.admin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class user {
    private String userId;
    private String userName;
    private int roleId;
    private String password;
    private String address;
    private String email;
    private String genderCd;
    private String phone;
    private Timestamp regDt;
    private String regId;
    private Timestamp modDt;
    private String modId;

}