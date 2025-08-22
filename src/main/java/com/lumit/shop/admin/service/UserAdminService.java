package com.lumit.shop.admin.service;

import com.lumit.shop.admin.dto.MemberSummaryResponseDto;

public interface UserAdminService {
    MemberSummaryResponseDto getSummary(int recentDays, int minReports, int limit);

    boolean suspend(String userId);

    boolean unsuspend(String userId);


}
