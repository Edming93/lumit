package com.lumit.shop.common.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.model.EmailMessage;
import com.lumit.shop.common.model.TbEmailAuth;

public interface EmailService {
    String sendMail(EmailMessage emailMessage);

    String createCode();

    String setContext(String code, String type);

    TbEmailAuth selectAuthInfo(String userId);

    ServiceCode insertAuthInfo(TbEmailAuth tbEmailAuth);

    ServiceCode updateAuthInfo(TbEmailAuth tbEmailAuth);

    ServiceCode grantAuthInfo(String userId);
}
