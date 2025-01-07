package com.lumit.shop.common.service;

import com.lumit.shop.common.constants.ServiceCode;
import com.lumit.shop.common.data.ModalInfo;
import com.lumit.shop.common.model.EmailMessage;
import jakarta.servlet.http.HttpSession;

public interface EmailService {
    ServiceCode sendMail(EmailMessage emailMessage);

    String createCode();

    String setContext(EmailMessage emailMessage);
}
