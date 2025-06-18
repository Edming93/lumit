package com.lumit.shop.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryCardDto {
    private String title;      // 카드 제목
    private String value;      // 숫자 값
    private String iconClass;  // 아이콘 (ex: "fa-user", "fa-users")
}
