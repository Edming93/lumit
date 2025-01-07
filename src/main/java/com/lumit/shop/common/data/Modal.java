package com.lumit.shop.common.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Modal {
    private String title;
    private String content;
}
