package com.lumit.shop.admin.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.lumit.shop.admin.dto.MemberSummaryResponseDto;
import com.lumit.shop.admin.service.UserAdminService;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserAdminRestController {

    private final UserAdminService service;

    // 요약: ?days=14&minReports=1&limit=10
    @GetMapping("/summary")
    public ResponseEntity<MemberSummaryResponseDto> getSummary(
            @RequestParam(defaultValue = "14") int days,
            @RequestParam(defaultValue = "1") int minReports,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(service.getSummary(days, minReports, limit));
    }

    @PostMapping("/{userId}/suspend")
    public ResponseEntity<Void> suspend(@PathVariable String userId) {
        return service.suspend(userId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}/unsuspend")
    public ResponseEntity<Void> unsuspend(@PathVariable String userId) {
        return service.unsuspend(userId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
