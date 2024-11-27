package com.lumit.shop.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class UserInfoDto {
    private String userId;
    private String name;
    private String current;
    private String password;
    private String address;
    private String email;
    private String phone;
    private String code;
    private String modId;
}
