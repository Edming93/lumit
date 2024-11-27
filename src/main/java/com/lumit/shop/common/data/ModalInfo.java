package com.lumit.shop.common.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ModalInfo {
    private String title;
    private String content;
    private String choice;
    private String name;
    private String type;
}
