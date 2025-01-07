package com.lumit.shop.common.controller;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.Modal;
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
    private final HttpSession session;

    @PostMapping("/findId")
    public ResponseEntity findId(@RequestBody Map<String, String> email) {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).subject("LUMIT ID 찾기").type("find-id").build();
        ServiceCode sc = emailService.sendMail(emailMessage);
        String title = "아이디 찾기";
        String content;
        if (sc.equals(ServiceCode.NOT_FOUND)) {
            content = "입력하신 이메일 주소로 가입된 회원이 없습니다.";
        } else {
            content = "메일이 전송되었습니다.";
        }
        setModalSession(title, content);
        return getResponse(sc);
    }

    @PostMapping("/password")
    public ResponseEntity sendPasswordMail(@RequestBody Map<String, String> data) {
        EmailMessage emailMessage = EmailMessage.builder().to(data.get("email")).userId(data.get("id")).subject("LUMIT 임시 비밀번호 발급").type("temp-password").build();
        ServiceCode sc = emailService.sendMail(emailMessage);
        String title = "임시 비밀번호 발급 받기";
        String content;
        if (sc.equals(ServiceCode.NOT_FOUND)) {
            content = "존재하지 않는 회원이거나 이메일 주소가 틀렸습니다.";
        } else if (sc.equals(ServiceCode.UNKNOWN)) {
            content = "오류가 발생하였습니다.<br>잠시후 다시 시도해주세요.";
        } else {
            content = "메일이 전송되었습니다.";
        }
        setModalSession(title, content);
        return getResponse(sc);
    }

    @PostMapping("/mail-check")
    public ResponseEntity sendJoinMail(@RequestBody Map<String, String> email) {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).subject("LUMIT 이메일 인증").type("mail-check").build();
        ServiceCode sc = emailService.sendMail(emailMessage);
        String title = "메일 주소 인증";
        String content;
        if (!sc.equals(ServiceCode.SUCCESS)) {
            content = "오류가 발생하였습니다.<br>잠시후 다시 시도해주세요.";
        } else {
            content = "메일이 전송되었습니다.";
        }
        setModalSession(title, content);
        return getResponse(sc);
    }

    @PostMapping("/change-email")
    public ResponseEntity changeEmailAddr(@RequestBody Map<String, String> email, HttpServletResponse response) throws IOException {
        EmailMessage emailMessage = EmailMessage.builder().to(email.get("email")).userId(email.get("id")).subject("LUMIT 이메일 인증").type("change-address").build();
        ServiceCode sc = emailService.sendMail(emailMessage);
        System.out.println(sc);
        String title = "메일 주소 변경";
        String content;
        if (sc.equals(ServiceCode.NOT_MODIFIED)) {
            content = "기존 이메일과 동일합니다.";
        } else if (sc.equals(ServiceCode.CONFLICT)) {
            content = "다른 아이디에서<br>이미 사용하고 있는 메일주소입니다.";
        } else if (sc.equals(ServiceCode.UNKNOWN)) {
            content = "오류가 발생하였습니다.<br>잠시 후 다시 시도해주세요.";
        } else {
            content = "메일이 전송되었습니다.";
        }
        setModalSession(title, content);
        return getResponse(sc);
    }

    private void setModalSession(String title, String content) {
        Modal modal = Modal.builder().title(title).content(content).build();
        session.setAttribute("modal", modal);
    }

    private ResponseEntity getResponse(ServiceCode sc) {
        switch (sc) {
            case NOT_FOUND:
                return ResponseEntity.status(404).build();
            case UNKNOWN:
                return ResponseEntity.status(503).build();
            case NOT_MODIFIED:
                return ResponseEntity.status(304).build();
            case CONFLICT:
                return ResponseEntity.status(409).build();
            default:
                return ResponseEntity.status(200).build();
        }
    }
}