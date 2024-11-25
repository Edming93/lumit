package com.lumit.shop.common.controller;

import com.lumit.shop.common.model.EmailMessage;
import com.lumit.shop.common.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/mail")
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/findId")
    public ResponseEntity findId(@RequestBody Map<String, String> email) {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).subject("LUMIT ID 찾기").build();
        String result = emailService.sendMail(emailMessage, "id");
        if (result == null || result.equals("")) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestBody Map<String, String> data) {
        EmailMessage emailMessage = EmailMessage.builder().to(data.get("email")).userId(data.get("id")).subject("LUMIT 임시 비밀번호 발급").build();
        String result = emailService.sendMail(emailMessage, "password");
        if (result == null || result.equals("")) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth")
    public ResponseEntity sendJoinMail(@RequestBody Map<String, String> email) {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).subject("LUMIT 이메일 인증").build();
        String result = emailService.sendMail(emailMessage, "email");
        if (result == null || result.equals("")) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().build();
    }
}
