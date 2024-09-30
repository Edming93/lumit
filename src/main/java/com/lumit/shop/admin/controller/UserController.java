package com.lumit.shop.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lumit.shop.common.service.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
//@PreAuthorize("hasAnyAuthority('1', '2')")
public class UserController {
    private final UserService userService;


    @GetMapping("/detail")
    public ResponseEntity user(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(userDetails.getUsername() + " / " + userDetails.getPassword(), HttpStatus.OK);
    }


}