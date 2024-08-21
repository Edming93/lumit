package com.lumit.shop.common.controller;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("")
    public String home() {
        return LUMIT_INDEX;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return LOGIN_FORM;
    }

    @GetMapping(MEMBER_PATH + "/createUser")
    public String signUp(Model model) {
        model.addAttribute("userDto", new TbLogin());
        return SIGNUP_FORM;
    }

    @PostMapping(MEMBER_PATH + "/createUser")
    public String signUp(@Valid @ModelAttribute("userDto") TbLogin userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_FORM;
        }
        try {
            TbLogin exists = userService.selectByUserId(userDto.getUserId());
            if (exists != null) {
                model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
                return SIGNUP_FORM;
            }
            userService.insertNewUser(userDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userDto", new TbLogin());
            return SIGNUP_FORM;
        }
        return "redirect:/";
    }
}
