package com.lumit.shop.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class TbEmailAuth {
    private String userId;
    private int type;
    private String email;
    private String code;
    private boolean isAuthenticated;
}
