package com.lumit.shop.common.controller;

import com.lumit.shop.admin.dto.UserDto;
import com.lumit.shop.admin.model.User;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final UserService userService;

    /**
     * GET Mapping Returns
     * <p>
     * GET /lumit -> /lumit/index
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

    @GetMapping("/lumit")
    public String getHome() {
        return LUMIT_INDEX;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("kakaoApiKey", kakaoApiKey);
        model.addAttribute("redirectUri", kakaoRedirectUri);

        return LOGIN_FORM;
    }

//    @GetMapping("/signup")
//    public String getSignUp() { return SIGNUP_FORM; }

    @GetMapping(MEMBER_PATH + "/createUser")
    public String signUp(Model model) {
        model.addAttribute("userDto", new UserDto());
        return SIGNUP_FORM;
    }

    @PostMapping(MEMBER_PATH + "/createUser")
    public String signUp(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult, Model model, HttpSession session) {
        System.out.println("타냐고용");
        System.out.println(userDto);
        if (bindingResult.hasErrors()) {
            System.out.println("타냐고용2222");
            return SIGNUP_FORM;
        }

        // TODO: User -> TB_LOGIN으로 분리, 서비스 로직 분리
        try {
            System.out.println("타냐고용3333");
            User user = User.createUser(userDto, passwordEncoder,session);

            System.out.println(user.getUserId());

            User exists = userService.findUserById(user.getUserId());
            if (exists != null) {
                model.addAttribute("errorMessage", "이미 존재하는 아이디입니다.");
                return SIGNUP_FORM;
            }
            System.out.println("타는겨마는겨!!!");
            userService.insertUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("userDto", new UserDto());
            return SIGNUP_FORM;
        }
        return "redirect:/";
    }
}
