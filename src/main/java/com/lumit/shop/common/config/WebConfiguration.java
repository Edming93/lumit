package com.lumit.shop.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final String resourcePath;
    private final String uploadPath;


    // application.yml에 설정한 path를 value에 넣기
    public WebConfiguration(@Value("${resource.path}") String resourcePath, @Value("${upload.path}") String uploadPath) {
        this.resourcePath = resourcePath;
        this.uploadPath = uploadPath;
    }

      @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(uploadPath)
                .addResourceLocations(resourcePath);
    }

}