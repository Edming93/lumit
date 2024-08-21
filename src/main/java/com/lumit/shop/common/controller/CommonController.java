package com.lumit.shop.common.controller;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class CommonController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    /**
     * GET Mapping Returns
     * <p>
     * GET / -> /lumit/index
     * GET /login -> /common/auth/login
     * GET /member/createUser -> /common/auth/signupForm
     */
    private final String LUMIT_INDEX = "/lumit/index";
    private final String LOGIN_FORM = "/common/auth/login";
    private final String SIGNUP_FORM = "/common/auth/signupForm";

    private final String MEMBER_PATH = "/member";

    @Value("${kakao.api_key}")
    private String kakaoApiKey;
    @Value("${kakao.redirect_uri}")
    private String kakaoRedirectUri;

    @GetMapping("")
    public String getHome() {
        return LUMIT_INDEX;
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        model.addAttribute("redirectUri", kakaoRedirectUri);

        return LOGIN_FORM;
    }

    @GetMapping(MEMBER_PATH + "/createUser")
    public String getSignUp(Model model) {
        model.addAttribute("tbLogin", new TbLogin());
        return SIGNUP_FORM;
    }

    @PostMapping(MEMBER_PATH + "/createUser")
    public String postSignUp(@Valid @ModelAttribute("tbLogin") TbLogin tbLogin, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_FORM;
        }
        tbLogin.setKakaoId((String) session.getAttribute("kakao_id"));
        ServiceCode sc = userService.insertUserControl(tbLogin);
        if (sc == ServiceCode.SUCCESS) {
            return "redirect:/";
        }
        if (sc == ServiceCode.CONFLICT) {
            model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
        } else {
            model.addAttribute("errorMessage", "알 수 없는 오류가 발생하였습니다.");
        }
        model.addAttribute("tbLogin", new TbLogin());
        return SIGNUP_FORM;
    }
}
