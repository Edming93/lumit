package com.lumit.shop.common.controller;

import com.lumit.shop.common.dto.ResponseDto;
import com.lumit.shop.common.model.TbAddress;
import com.lumit.shop.common.model.User;
import com.lumit.shop.common.security.PrincipalDetails;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class APIController {


    private final UserService userService;

    // 멤버 - 회원가입 - 중복체크api
    @GetMapping(value = "/opened/idCheck")
    public @ResponseBody ResponseDto<?> idDuplicateCheck(@RequestParam(value = "id") String id) {
        System.out.println(id);
        if (id == null || id.isEmpty()) {
            return new ResponseDto<>("아이디를 입력해주세요", null);
        }
        if (userService.isIdDuplicated(id)) {
            return new ResponseDto<>("동일한 아이디가 존재합니다.", false);
        } else {
            return new ResponseDto<>("사용가능한 아이디입니다.", true);
        }
    }

    /**
     * 개방된 api 메소드 매개 변수에 userId를 넣는 것이 보안의 문제가 있다고 판단되어
     * authentication의 정보를 얻어와서 반환하는 것으로 변경하였습니다.
     *
     * @return
     */
    @GetMapping(value = "/opened/addressList")
    public @ResponseBody ResponseDto<?> addressList(HttpServletResponse response) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object obj = authentication.getPrincipal();
        User user = null;
        if (obj instanceof User) {
            user = (User) obj;
        } else if (obj instanceof PrincipalDetails) {
            user = ((PrincipalDetails) obj).getUser();
        } else {
            response.sendRedirect("/auth/accessDenied");
            return null;
        }
        List<TbAddress> addressList = userService.selectAddressListByUserId(user.getUserId());
        return new ResponseDto<>("", addressList);
    }
}
