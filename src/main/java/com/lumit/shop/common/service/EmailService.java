package com.lumit.shop.common.service;

import com.lumit.shop.common.controller.GlobalController;
import com.lumit.shop.common.model.EmailMessage;
import com.lumit.shop.common.model.TbLogin;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    private final UserService userService;

    @Value("${lumit.siteId}")
    String siteId;

    public String sendMail(EmailMessage emailMessage, String type) {
        String authNum = createCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        TbLogin tbLogin = null;
        if (type.equals("password")) {
            tbLogin = userService.selectByUserId(emailMessage.getUserId());
            if (tbLogin == null || !tbLogin.getEmail().equals(emailMessage.getTo())) {
                return null;
            }
            userService.updateTempPwd(emailMessage.getUserId(), authNum);
        }
        if (type.equals("id")) {
            tbLogin = userService.selectByEmail(emailMessage.getTo());
            if (tbLogin == null) {
                return null;
            }
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            if (type.equals("id")) {
                mimeMessageHelper.setText(setContext(tbLogin.getUserId(), type), true);
            } else {
                mimeMessageHelper.setText(setContext(authNum, type), true);
            }
            javaMailSender.send(mimeMessage);
            log.info("Success");
            return authNum;
        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }

    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 16; i++) {
            int index = random.nextInt(3);
            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                default:
                    key.append(random.nextInt(10));
            }
        }
        return key.toString();
    }

    public String setContext(String code, String type) {
        Context context = new Context();
        context.setVariable("code", code);

        context.setVariable("siteId", siteId);
        String htmlPath = null;
        if (type.equals("email")) {
            htmlPath = "emailTemplates/authEmail";
        } else if (type.equals("password")) {
            htmlPath = "emailTemplates/tempPwdEmail";
        } else if (type.equals("id")) {
            htmlPath = "emailTemplates/findIdEmail";
        }
        return templateEngine.process(htmlPath, context);
    }
}
