package com.lumit.shop.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SearchUserDto {
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String socialId;
}
