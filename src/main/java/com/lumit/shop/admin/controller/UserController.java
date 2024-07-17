package com.lumit.shop.admin.controller;

import com.lumit.shop.common.model.User;
import com.lumit.shop.common.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/user")
    public List<User> user() {
        return userService.getUserList();
    }

    @GetMapping("/user/detail")
    public ResponseEntity user(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(userDetails.getUsername() + " / " + userDetails.getPassword(), HttpStatus.OK);
    }

    @GetMapping("/api1")
    public ResponseEntity api1() {
        return new ResponseEntity<>("api1 입니다.", HttpStatus.OK);
    }

    @GetMapping("/api2")
    public ResponseEntity api2() {
        return new ResponseEntity<>("api2 입니다.", HttpStatus.OK);
    }

}