package com.lumit.shop.admin.controller.rest;

import com.lumit.shop.admin.dto.ReportCreateDto;
import com.lumit.shop.admin.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportRestController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ReportCreateDto dto) {
        reportService.report(dto);
        return ResponseEntity.ok().build();
    }
}
