package com.lumit.shop.common.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {
	public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String CHARSET_UTF8 = ";charset=UTF-8";

    private ResponseBuilder() {
        throw new UnsupportedOperationException();
    }

    public static ResponseEntity<Map<String, Object>> build(Map<String, Object> resMap, HttpStatus httpStatus) {
        return build(resMap, httpStatus, httpStatus.getReasonPhrase());
    }
    
    public static ResponseEntity<Map<String, Object>> build(Map<String, Object> resMap, HttpStatus httpStatus, String message) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HEADER_CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + CHARSET_UTF8);
        Map<String, Object> httpBody = new HashMap<String, Object>();
        if(httpStatus == HttpStatus.OK) {
        	httpBody.put("resCode", "0000");
            httpBody.put("resMsg", "Success");	
            httpBody.put("result", true);
        } else {
        	httpBody.put("resCode", "9" + httpStatus.value());
            httpBody.put("resMsg", message);
        }
        
        if(resMap != null) {
        	httpBody.putAll(resMap);	
        }
        
        return new ResponseEntity<Map<String, Object>>(httpBody, httpHeaders, httpStatus);
    }
}
