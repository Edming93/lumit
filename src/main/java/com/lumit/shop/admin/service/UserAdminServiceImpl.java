package com.lumit.shop.admin.service;

import com.lumit.shop.admin.dto.MemberSummaryDto;
import com.lumit.shop.admin.dto.MemberSummaryResponseDto;
import com.lumit.shop.admin.repository.UserAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {
    private final UserAdminRepository userAdminRepository;

    public MemberSummaryResponseDto getSummary(int recentDays, int minReports, int limit) {
        List<MemberSummaryDto> flagged = userAdminRepository.selectFlaggedMembers(minReports, limit);
        List<MemberSummaryDto> recent = userAdminRepository.selectRecentMembers(recentDays, limit);
        return MemberSummaryResponseDto.builder()
                .flagged(flagged)
                .recent(recent)
                .build();
    }

    public boolean suspend(String userId) {
        return userAdminRepository.suspendUser(userId) > 0;
    }

    public boolean unsuspend(String userId) {
        return userAdminRepository.unsuspendUser(userId) > 0;
    }
}
