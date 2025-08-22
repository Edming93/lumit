package com.lumit.shop.admin.repository;

import com.lumit.shop.admin.dto.ReportCreateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReportRepository {
    int insertReport(ReportCreateDto dto);

    List<String> selectLatestReasons(String userId,
                                     int limit);

    List<UserReportCountRow> selectReportCounts(List<String> userIds);
    
}
