package com.lumit.shop.admin.controller.restController;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.admin.dto.SummaryCardDto;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
public class UserRestController {

    private final UserService userService;

    // 🔹 현재 로그인 유저 정보
    @GetMapping("/me")
    public ResponseEntity<?> getSelf() {
        User currentUser = getCurrentUser();
        System.out.println(currentUser);
        return ResponseEntity.ok(currentUser);
    }

    // 🔹 활성 운영자 목록
    @GetMapping("/list")
    public ResponseEntity<?> getAdminList() {
        User currentUser = getCurrentUser();
        List<User> adminList = userService.selectAdminList();
        List<User> sortedList = userService.sortWithCurrentUserFirst(adminList, currentUser.getUserId());
        return ResponseEntity.ok(sortedList);
    }

    @GetMapping("/old-list")
    public ResponseEntity<?> getOldAdminList() {
        List<User> oldAdminList = userService.selectOldAdminList();

        List<Map<String, Object>> result = oldAdminList.stream().map(user -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", user.getUserId());
            map.put("name", user.getName());
            map.put("modId", user.getModId());
            map.put("modDt", user.getModDt());
            return map;
        }).toList();

        return ResponseEntity.ok(result);
    }

    // 🔹 운영자 정보 수정 (이름 / 권한)
    @PatchMapping("/info/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable("id") String id,
                                         @RequestBody AdminDto adminDto) {
        ServiceCode result = userService.updateAdmin(id, adminDto);
        if (!result.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // 🔹 운영자 권한 삭제
    @DeleteMapping("/info/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable("id") String id) {
        User currentUser = getCurrentUser();
        if (currentUser.getUserId().equals(id)) {
            return ResponseEntity.badRequest().body("본인의 권한은 삭제할 수 없습니다.");
        }

        ServiceCode result = userService.deleteAdmin(id);
        if (!result.equals(ServiceCode.DELETED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }


    // 🔹 관리자 요약 카드 (이미 존재하는 코드지만 유지)
    @GetMapping("/summary/manager")
    public List<SummaryCardDto> getManagerSummary() {
        long adminCount = userService.countAdmins();
        long recentActiveAdminCount = userService.countRecentlyActiveAdmins();
        long recentlyRegisteredAdminCount = userService.countRecentlyRegisteredAdmins();

        return List.of(
                new SummaryCardDto("관리자 수", String.valueOf(adminCount), "fa-user-shield"),
                new SummaryCardDto("최근 활동 관리자", String.valueOf(recentActiveAdminCount), "fa-clock"),
                new SummaryCardDto("최근 등록 관리자", String.valueOf(recentlyRegisteredAdminCount), "fa-user-plus")
        );
    }

    @GetMapping("/summary/customers")
    public List<SummaryCardDto> getCustomersSummary() {
        /**
         * todo
         * 실제 데이터 구현해야함
         */
        long adminCount = userService.countAllCustomers();
        long recentActiveAdminCount = userService.countRecentlyActiveAdmins();
        long recentlyRegisteredAdminCount = userService.countRecentlyRegisteredAdmins();

        return List.of(
                new SummaryCardDto("총 회원 수", String.valueOf(adminCount), "fa-users"),
                new SummaryCardDto("7일간 가입자 수", String.valueOf(recentActiveAdminCount), "fa-user-plus"),
                new SummaryCardDto("신고된 건 수", String.valueOf(recentlyRegisteredAdminCount), "fa-circle-exclamation")
        );
    }

    // 🔹 유틸 - 현재 로그인 유저 꺼내기
    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
