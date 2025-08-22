package com.lumit.shop.admin.service;

import com.lumit.shop.admin.dto.ReportCreateDto;
import com.lumit.shop.admin.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public void report(ReportCreateDto dto) {

        reportRepository.insertReport(dto);
    }
}
