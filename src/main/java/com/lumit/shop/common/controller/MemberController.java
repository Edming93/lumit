package com.lumit.shop.common.controller;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.security.social.CustomOauth2UserDetails;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping(value = "/main/member")
@RequiredArgsConstructor
public class MemberController {
    private final String SIGNUP_FORM = "/common/auth/signupForm";
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @GetMapping("")
    public String userInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (authentication.getPrincipal() instanceof User) {
            user = (User) authentication.getPrincipal();
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            CustomOauth2UserDetails details = (CustomOauth2UserDetails) authentication.getPrincipal();
            user = (User) userDetailsService.loadUserByUsername(details.getUsername());
        }
        model.addAttribute("user", user);
        return "/member/userInfo";
    }

    @GetMapping("/createUser")
    public String getSignUp(Principal principal, HttpSession session, Model model) {
        TbLogin tbLogin = new TbLogin();
        if (principal != null) {
            TbLogin user = userService.selectByUserId(principal.getName());

            if (user != null) {
                System.out.println(user);
                user.setPhone("");
                user.setGenderCd("");
                user.setUserId("");
                user.setPassword("");
                user.setAddress("");
                model.addAttribute("tbLogin", user);
                model.addAttribute("message", "소셜 가입을 완료하기 위해 추가정보를 입력합니다.");
            } else {
                model.addAttribute("tbLogin", tbLogin);
            }
        } else {
            tbLogin.setPhone("");
            tbLogin.setGenderCd("");
            tbLogin.setUserId("");
            tbLogin.setPassword("");
            tbLogin.setAddress("");
            model.addAttribute("tbLogin", tbLogin);
        }
        return SIGNUP_FORM;
    }

    @PostMapping("/createUser")
    public String postSignUp(@Valid @ModelAttribute("tbLogin") TbLogin tbLogin, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_FORM;
        }
        tbLogin.setSocialId((String) session.getAttribute("social_id"));
        ServiceCode sc;
        if (tbLogin.getSocialId() != null) {
            sc = userService.updateSocialUser(tbLogin);
        } else {
            sc = userService.insertUserControl(tbLogin);
        }
        if (sc == ServiceCode.SUCCESS) {
            return "redirect:/";
        }
        if (sc == ServiceCode.UPDATED) {
            return "redirect:/logout";
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
