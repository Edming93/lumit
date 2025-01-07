package com.lumit.shop.common.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.dto.UserInfoDto;
import com.lumit.shop.common.model.EmailMessage;
import com.lumit.shop.common.model.TbLogin;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final HttpSession session;
    private final UserService userService;

    @Value("${lumit.siteId}")
    String siteId;

    @Transactional
    public ServiceCode sendMail(EmailMessage emailMessage) {
        String authNum = createCode();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        TbLogin tbLogin = null;
        String type = emailMessage.getType();
        emailMessage.setCode(authNum);
        ServiceCode sc;
        switch (type) {
            case "temp-password":
                tbLogin = userService.selectByUserId(emailMessage.getUserId());
                if (tbLogin == null || !tbLogin.getEmail().equals(emailMessage.getTo())) {
                    return ServiceCode.NOT_FOUND;
                }
                UserInfoDto userInfo = UserInfoDto.builder().userId(tbLogin.getUserId()).password(authNum).build();
                sc = userService.updateTempPwd(userInfo);
                if (!sc.equals(ServiceCode.UPDATED)) {
                    return ServiceCode.UNKNOWN;
                }
                break;
            case "find-id":
                tbLogin = userService.selectByEmail(emailMessage.getTo());
                if (tbLogin == null) {
                    return ServiceCode.NOT_FOUND;
                }
                emailMessage.setUserId(tbLogin.getUserId());
                break;
            case "mail-check":
                break;
            case "change-address":
                tbLogin = userService.selectByEmail(emailMessage.getTo());
                if (tbLogin != null) {
                    if (tbLogin.getUserId().equals(emailMessage.getUserId())) {
                        return ServiceCode.NOT_MODIFIED;
                    }
                    return ServiceCode.CONFLICT;
                }
                userInfo = UserInfoDto.builder().userId(emailMessage.getUserId()).code(authNum).build();
                sc = userService.updateUserInfo(userInfo);
                if (!sc.equals(ServiceCode.UPDATED)) {
                    return ServiceCode.UNKNOWN;
                }
            default:
                break;
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(emailMessage.getTo());
            mimeMessageHelper.setSubject(emailMessage.getSubject());
            mimeMessageHelper.setText(setContext(emailMessage), true);
            javaMailSender.send(mimeMessage);
            log.info("Success");
            return ServiceCode.SUCCESS;
        } catch (MessagingException e) {
            log.info("fail");
            return ServiceCode.UNKNOWN;
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

    public String setContext(EmailMessage emailMessage) {
        Context context = new Context();
        if (emailMessage.getCode() != null) {
            context.setVariable("code", emailMessage.getCode());
        }
        if (emailMessage.getUserId() != null) {
            context.setVariable("id", emailMessage.getUserId());
        }
        context.setVariable("email", emailMessage.getTo());
        context.setVariable("siteId", siteId);
        String htmlPath = null;
        String type = emailMessage.getType();
        switch (type) {
            case "email":
                htmlPath = "emailTemplates/authEmail";
                break;
            case "temp-password":
                htmlPath = "emailTemplates/tempPwdEmail";
                break;
            case "find-id":
                htmlPath = "emailTemplates/findIdEmail";
                break;
            case "change-address":
                htmlPath = "emailTemplates/changeEmail";
                break;
        }
        return templateEngine.process(htmlPath, context);
    }

}
