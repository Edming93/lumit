package com.lumit.shop.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userId;
    private String userName;
    private String roleId;
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