package com.lumit.shop.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class EmailMessage {
    private String userId;
    private String to;
    private String subject;
    private String message;
    private String type;
    private String code;
}
