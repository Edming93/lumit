package com.lumit.shop.common.service;

import com.lumit.shop.common.model.EmailMessage;

public interface EmailService {
    String sendMail(EmailMessage emailMessage);

    String createCode();

    String setContext(EmailMessage emailMessage);
}
