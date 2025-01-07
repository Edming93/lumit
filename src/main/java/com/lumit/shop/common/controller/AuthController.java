package com.lumit.shop.common.controller;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.Modal;
import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.dto.UserInfoDto;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.service.EmailService;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping(value = "/accessDenied")
    public String accessDenied() {
        return "/error/403";
    }

    @GetMapping(value = "/emailCheck")
    public String setEmailAuth(String id, String code, String email, String type, HttpSession session) {
        ServiceCode sc;
        Modal modal;
        String title;
        String content;
        if (type.equals("join")) {

        } else if (type.equals("change")) {
            title = "이메일 변경";
            TbLogin tbLogin = userService.selectByUserId(id);
            if (tbLogin.getAuthCode() == null) {
                content = "만료된 인증코드입니다.<br>인증 코드를 다시 발급받아주세요.";
            } else if (!tbLogin.getAuthCode().equals(code)) {
                content = "인증 코드가 올바르지 않습니다.<br>메일함을 새로고침해주세요.";
            } else {
                UserInfoDto userInfoDto = UserInfoDto.builder().userId(id).email(email).build();
                sc = userService.updateUserInfo(userInfoDto);
                if (sc.equals(ServiceCode.UPDATED)) {
                    content = "이메일 변경이 완료되었습니다.";
                } else {
                    content = "이메일 변경이 실패하였습니다.<br>잠시 후 다시 시도해주세요.";
                }
            }
            session.setAttribute("modal", Modal.builder().title(title).content(content).build());
            return "redirect:/main/member/edit";
        }
        return null;
    }
}
