package com.lumit.shop.common.controller;

import com.lumit.shop.common.dto.ResponseDto;
import com.lumit.shop.common.model.TbAddress;
import com.lumit.shop.common.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/opened/addressList")
    public @ResponseBody ResponseDto<?> addressList(@RequestParam(value = "userId") String userId) {
        List<TbAddress> addressList = userService.selectAddressListByUserId(userId);
        return new ResponseDto<>("", addressList);
    }
}
