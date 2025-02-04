package com.lumit.shop.common.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TbCategory {
    private int categoryId;
    private String categoryName;
    private String useYn;
    private int parent;
}
