package com.lumit.shop.controller;

import com.lumit.shop.admin.dto.UserDto;
import com.lumit.shop.admin.model.Role;
import com.lumit.shop.admin.model.User;
import com.lumit.shop.common.dto.SignUpDto;
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


    public User createManager(String regAdmin) {
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

    public User createUser() {
        UserDto userDto = new UserDto();
        userDto.setUserId("asdf11");
        userDto.setEmail("asdf@asdf.com");
        userDto.setPassword("1234");
        userDto.setName("홍길동");
        userDto.setAddress("서울시 마포구 서교동");
        userDto.setRegAdmin(userDto.getUserId());
        userDto.setPhone("010-1234-1234");
        userDto.setGenderCd("1");
        userDto.setRole(Role.USER);
        return User.createUser(userDto, passwordEncoder);
    }

    @Test
    @DisplayName("id로 유저 조회하기 테스트")
    public void findUserByIdTest() throws Exception {
        User user = new User();
        user.setUserId("admin");
        user.setRoleId(2);
        user.setPassword(passwordEncoder.encode("1234"));
        user.setUserName("ssdd");
        user.setGenderCd("1");
        user.setEmail("sadf@dfa.com");
        user.setPhone("01030303848");
        user.setAddress("dfjaksdf");
        user.setRegId("afddasffd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        user.setRegDt(new Timestamp((sdf.parse("2024-08-07 05:21:00").getTime())));
        user.setModId(null);
        user.setModDt(new Timestamp((sdf.parse("2024-08-07 05:21:00").getTime())));
        Assertions.assertEquals(user, userService.findUserById("admin"));
    }

    @Test
    @DisplayName("관리자 생성 테스트")
    @WithMockUser(username = "admin", roles = "SUPER_ADMIN")
    public void addNewAdminTest() throws Exception {
        User user = this.createManager("admin");
        System.out.println(user);
        Assertions.assertEquals(1, userService.addNewUser(user));
    }

    @Test
    @DisplayName("일반유저 생성 테스트")
    public void addNewUserTest() throws Exception {
        User user = this.createUser();
        System.out.println(user);
        Assertions.assertEquals(1, userService.addNewUser(user));
    }
}
