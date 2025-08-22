package com.lumit.shop.admin.dto;

import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSummaryResponseDto {
    private List<MemberSummaryDto> flagged; // 신고 이력 있는 회원
    private List<MemberSummaryDto> recent;  // 최근 가입 회원
}