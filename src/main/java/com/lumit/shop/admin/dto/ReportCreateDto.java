package com.lumit.shop.admin.dto;

import com.lumit.shop.admin.model.ReportReason;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportCreateDto {
    private String reportedUserId;
    private String reporterUserId;
    private ReportReason reason;
    private String reasonDetail;
    private String contextType;
    private String contextId;
}