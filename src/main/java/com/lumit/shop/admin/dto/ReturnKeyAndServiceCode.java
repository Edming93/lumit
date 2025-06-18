package com.lumit.shop.admin.dto;

import com.lumit.shop.common.constants.ServiceCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReturnKeyAndServiceCode {
    private Integer id;
    private ServiceCode sc;
}
