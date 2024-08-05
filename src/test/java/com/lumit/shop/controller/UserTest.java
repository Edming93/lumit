package com.lumit.shop.controller;

import com.lumit.shop.admin.dto.UserDto;
import com.lumit.shop.admin.model.Role;
import com.lumit.shop.admin.model.User;
import com.lumit.shop.common.service.UserService;
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


    public User createUser(String regAdmin) {
        UserDto userDto = new UserDto();
        userDto.setUserId("manager1");
        userDto.setEmail("asdf@asdf.com");
        userDto.setPassword("1234");
        userDto.setName("홍길동");
        userDto.setAddress("서울시 은평구 구산동");
        userDto.setRegAdmin(regAdmin);
        userDto.setPhone("0001111111");
        userDto.setGenderCd("1");
        userDto.setRole(Role.ADMIN);
        User user = User.createAdmin(userDto, passwordEncoder);
        return user;
    }

    @Test
    @DisplayName("관리자 생성 테스트")
    @WithMockUser(username = "admin", roles = "SUPER_ADMIN")
    public void addNewAdminTest() throws Exception {
        User user = this.createUser("admin");
        System.out.println(user);
        Assertions.assertEquals(1, userService.addNewUser(user));
    }
}
