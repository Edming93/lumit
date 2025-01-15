package com.lumit.shop.admin.controller.restController;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;

    @DeleteMapping(value = "/info/{id}")
    public @ResponseBody ResponseEntity<?> deleteRole(@PathVariable("id") String id) {
        ServiceCode result = userService.deleteAdmin(id);
        if (!result.equals(ServiceCode.DELETED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/info/{id}")
    public @ResponseBody ResponseEntity<?> updateAdmin(@PathVariable("id") String id, @RequestBody AdminDto adminDto) {
        ServiceCode result = userService.updateAdmin(id, adminDto);
        if (!result.equals(ServiceCode.UPDATED)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
