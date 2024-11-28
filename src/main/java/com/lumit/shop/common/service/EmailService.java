package com.lumit.shop.common.service;

import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.model.EmailMessage;
import jakarta.servlet.http.HttpSession;

public interface EmailService {
    ModalInfo sendMail(EmailMessage emailMessage);

    String createCode();

    String setContext(EmailMessage emailMessage);
}
