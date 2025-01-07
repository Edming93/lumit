package com.lumit.shop.common.controller;

import com.lumit.shop.common.data.Modal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalController {
    private final HttpSession session;

    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

    @ModelAttribute("contextPath")
    String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    void setModalSession(String title, String content) {
        Modal modal = Modal.builder().title(title).content(content).build();
        session.setAttribute("modal", modal);
    }
}