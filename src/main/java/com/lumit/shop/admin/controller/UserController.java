package com.lumit.shop.admin.controller;

import com.lumit.shop.admin.model.user;
import com.lumit.shop.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/user")
    public List<user> user() {
        return userService.getUserList();
    }

    @GetMapping("/user/detail")
    public ResponseEntity user(@AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(userDetails.getUsername() + " / " +userDetails.getPassword(), HttpStatus.OK);
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