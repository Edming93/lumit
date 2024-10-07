package com.lumit.shop.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lumit.shop.common.service.StringUtils;

/* 자바 함수를 타임리프에서 사용 할 경우 bean등록 해주는 영역*/

@Configuration
public class ThymeleafConfig {

    // 유틸리티 객체를 Bean으로 등록
    @Bean(name = "StringUtils")
    public StringUtils stringUtils() {
        return new StringUtils();
    }
}