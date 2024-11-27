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
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).subject("LUMIT ID 찾기").type("find-id").build();
        String result = emailService.sendMail(emailMessage);
        if (result == null || result.equals("")) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestBody Map<String, String> data) {
        EmailMessage emailMessage = EmailMessage.builder().to(data.get("email")).userId(data.get("id")).subject("LUMIT 임시 비밀번호 발급").type("temp-password").build();
        String result = emailService.sendMail(emailMessage);
        if (result == null || result.equals("")) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/mail-check")
    public ResponseEntity sendJoinMail(@RequestBody Map<String, String> email) {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).subject("LUMIT 이메일 인증").type("mail-check").build();
        String result = emailService.sendMail(emailMessage);
        if (result == null || result.equals("")) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change-email")
    public ResponseEntity changeEmailAddr(@RequestBody Map<String, String> email) {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).userId(email.get("id")).subject("LUMIT 이메일 인증").type("change-address").build();
        System.out.println(emailMessage.toString());
//
//        String result = emailService.sendMail(emailMessage);
//        if (result == null || result.equals("")) {
//            return ResponseEntity.status(204).build();
//        }
        return ResponseEntity.ok().build();
    }
}
