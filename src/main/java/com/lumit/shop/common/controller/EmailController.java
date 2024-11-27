package com.lumit.shop.common.controller;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.model.EmailMessage;
import com.lumit.shop.common.service.EmailService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
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
    public ResponseEntity changeEmailAddr(@RequestBody Map<String, String> email, HttpServletResponse response, HttpSession session) throws IOException {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).userId(email.get("id")).subject("LUMIT 이메일 인증").type("change-address").build();
        String result = emailService.sendMail(emailMessage);
        ModalInfo modalInfo = null;
        if (result.equals(ServiceCode.UNKNOWN.name())) {
            modalInfo = ModalInfo.builder().title("이메일 전송 실패").type("simple").choice("").name("emailFail").content("이메일 전송에 실패하였습니다.<br>잠시 후 다시 시도해주세요.").build();
        } else if (result.equals(ServiceCode.CONFLICT.name())) {
            modalInfo = ModalInfo.builder().title("이메일 중복").type("simple").choice("").name("emailConflict").content("현재 이메일과 동일합니다.").build();
        } else {
            modalInfo = ModalInfo.builder().title("이메일 전송 완료").type("simple").choice("").name("emailChangeSuccess").content("이메일이 전송되었습니다.<br>메일함을 확인해주세요.").build();
        }
        session.setAttribute("modalInfo", modalInfo);
        return ResponseEntity.ok().body(modalInfo);
    }
}
