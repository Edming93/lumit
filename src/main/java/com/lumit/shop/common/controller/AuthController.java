package com.lumit.shop.common.controller;

import com.lumit.shop.common.constants.ServiceCode;
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
        if (type.equals("join")) {

        } else if (type.equals("change")) {
            TbLogin tbLogin = userService.selectByUserId(id);
            if (!tbLogin.getAuthCode().equals(code)) {
                return "/error/wrong-code";
            }
            UserInfoDto userInfoDto = UserInfoDto.builder().userId(id).email(email).build();
            ServiceCode sc = userService.updateUserInfo(userInfoDto);
            ModalInfo modalInfo = null;
            if (!sc.equals(ServiceCode.UPDATED)) {
                modalInfo = ModalInfo.builder().title("이메일 변경 실패").type("simple").choice("").name("emailFail").content("이메일을 변경하지 못하였습니다.<br>잠시 후 다시 시도해주세요.").build();
            } else {
                modalInfo = ModalInfo.builder().title("이메일 변경 완료").type("simple").choice("").name("emailSuccess").content(String.format("이메일 주소가 %s로 변경되었습니다.", email)).build();
            }
            session.setAttribute("modalInfo", modalInfo);
            return "redirect:/main/member/edit";
        }
        return null;
    }
}
