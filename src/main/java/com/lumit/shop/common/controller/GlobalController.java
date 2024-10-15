package com.lumit.shop.common.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    /**
     * 현재위치를
     *
     * @param request
     * @return
     */
    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }

    @ModelAttribute("contextPath")
    String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }
}