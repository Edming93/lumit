package com.lumit.shop.admin.repository;

import java.util.List;

import com.lumit.shop.admin.dto.MemberSummaryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserAdminRepository {
    List<MemberSummaryDto> selectFlaggedMembers(int minReports, int limit);

    List<MemberSummaryDto> selectRecentMembers(int days, int limit);

    int suspendUser(String userId);

    int unsuspendUser(String userId);
}