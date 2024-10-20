package com.lumit.shop.common.controller;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.dto.SignUpDto;
import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.security.PrincipalDetails;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping(value = "/main/member")
@RequiredArgsConstructor
public class MemberController {
    private final String SIGNUP_FORM = "/common/auth/signupForm";
    private final String FIND_USER = "/common/auth/findUser";
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @GetMapping("")
    public String userInfo(Model model) {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object obj = authentication.getPrincipal();
        if (obj instanceof User) {
            user = (User) obj;
        } else if (obj instanceof PrincipalDetails) {
            user = ((PrincipalDetails) obj).getUser();
        }
        model.addAttribute("user", user);
        return "/member/userInfo";
    }

    @GetMapping("/createUser")
    public String getSignUp(Principal principal, HttpSession session, Model model) {
        SignUpDto signUpDto = new SignUpDto();
        if (principal != null) {
            TbLogin user = userService.selectByUserId(principal.getName());
            if (user != null) {
                System.out.println(user);
                user.setPhone("");
                user.setGenderCd("");
                user.setUserId("");
                user.setPassword("");
                user.setAddress("");
                signUpDto = SignUpDto.of(user);
                model.addAttribute("signUpDto", signUpDto);
                model.addAttribute("message", "소셜 가입을 완료하기 위해 추가정보를 입력합니다.");
            } else {
                model.addAttribute("signUpDto", signUpDto);
            }
        } else {
            signUpDto.setPhone("");
            signUpDto.setGenderCd("");
            signUpDto.setUserId("");
            signUpDto.setPassword("");
            signUpDto.setAddress("");
            model.addAttribute("signUpDto", signUpDto);
        }
        return SIGNUP_FORM;
    }

    @PostMapping("/createUser")
    public String postSignUp(@Valid @ModelAttribute("signUpDto") SignUpDto signUpDto, BindingResult bindingResult, Model model, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return SIGNUP_FORM;
        }
        signUpDto.setSocialId((String) session.getAttribute("social_id"));
        ServiceCode sc;
        if (signUpDto.getSocialId() != null) {
            sc = userService.updateSocialUser(signUpDto);
        } else {
            sc = userService.insertUserControl(signUpDto);
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
        model.addAttribute("signUpDto", new SignUpDto());
        return SIGNUP_FORM;
    }

    @GetMapping("/findUser")
    public String findUser(Model model, @RequestParam(value = "info", required = false) String info) {
        if ((info != null) && info.equals("pwd")) {
            model.addAttribute("info", "pwd");
        } else {
            model.addAttribute("info", "id");
        }
        return FIND_USER;
    }

}
