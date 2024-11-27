package com.lumit.shop.common.model;

import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.dto.UserInfoDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

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
    private static ModelMapper modelMapper = new ModelMapper();

    public User userMapping() {
        return modelMapper.map(this, User.class);
    }

    public static TbLogin of(User user) {
        return modelMapper.map(user, TbLogin.class);
    }
}