package com.lumit.shop.common.model;

import com.lumit.shop.common.dto.SignUpDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.math.BigInteger;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class TbLogin {
    private String userId;
    private int roleId;
    private String name;
    private String password;
    private String address;
    private String email;
    private String genderCd;
    private String phone;
    private String socialId;
    private Timestamp regDt;
    private String regId;
    private Timestamp modDt;
    private String modId;
    private int defaultAddr;
}