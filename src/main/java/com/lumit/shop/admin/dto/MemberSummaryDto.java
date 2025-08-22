package com.lumit.shop.admin.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSummaryDto {
    private String userId;
    private String name;
    private String email;
    private String status;       // ACTIVE / WARNING / SUSPENDED
    private Integer reportCount; // null 허용
    private LocalDateTime regDt;
}
