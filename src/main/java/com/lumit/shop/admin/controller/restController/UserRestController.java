package com.lumit.shop.admin.controller.restController;

import com.lumit.shop.admin.dto.AdminDto;
import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/user")
public class UserRestController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/me")
    public @ResponseBody ResponseEntity<?> getSelf() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/managers")
    public @ResponseBody ResponseEntity<?> selectAdminList() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentUserId = currentUser.getUserId();

        List<User> adminList = userService.selectAdminList();
        List<User> sortedList = userService.sortWithCurrentUserFirst(adminList, currentUserId);
        return ResponseEntity.ok(sortedList);
    }

    @PostMapping("/new-manager")
    public String createUser(@RequestBody TbLogin tbLogin,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/user/userForm";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();

        tbLogin.setEmail(tbLogin.getUserId() + "@lumit.com");
        tbLogin.setPhone("추후 입력 요망");
        tbLogin.setAddress("추후 입력 요망");
        tbLogin.setRegId(currentUser.getUserId());
        tbLogin.setPassword(passwordEncoder.encode(tbLogin.getPassword()));

        try {
            userService.insertAdmin(tbLogin);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "admin/user/userForm";
        }

        return "redirect:/admin/user";
    }

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
