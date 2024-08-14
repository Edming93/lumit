package com.lumit.shop.controller;

import com.lumit.shop.common.model.TbLogin;
import com.lumit.shop.common.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    HttpSession session;


    public TbLogin createManager(String regAdmin) {
        TbLogin userDto = new TbLogin();
        userDto.setUserId("manager1");
        userDto.setEmail("asdf@asdf.com");
        userDto.setPassword(passwordEncoder.encode("1234"));
        userDto.setName("홍길동");
        userDto.setAddress("서울시 은평구 구산동");
        userDto.setRegId(regAdmin);
        userDto.setPhone("0001111111");
        userDto.setGenderCd("1");
        userDto.setRoleId(1);
        return userDto;
    }

    public TbLogin createUser() {
        TbLogin userDto = new TbLogin();
        userDto.setUserId("asdf11");
        userDto.setEmail("asdf@asdf.com");
        userDto.setPassword(passwordEncoder.encode("1234"));
        userDto.setName("홍길동");
        userDto.setAddress("서울시 마포구 서교동");
        userDto.setRegId(userDto.getUserId());
        userDto.setPhone("010-1234-1234");
        userDto.setGenderCd("1");
        userDto.setRoleId(3);
        return userDto;
    }

    @Test
    @DisplayName("id로 유저 조회하기 테스트")
    public void selectByUserIdTest() throws Exception {

    }

    @Test
    @DisplayName("관리자 생성 테스트")
    @WithMockUser(username = "admin", roles = "SUPER_ADMIN")
    public void addNewAdminTest() throws Exception {

    }

    @Test
    @DisplayName("일반유저 생성 테스트")
    public void insertNewUserTest() throws Exception {
    }
}
