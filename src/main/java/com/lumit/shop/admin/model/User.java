package com.lumit.shop.admin.model;

import com.lumit.shop.admin.dto.UserDto;
import com.lumit.shop.common.dto.SignUpDto;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
//@ToString
public class User {
    private String userId;
    private Integer roleId;
    private String userName;
    private String password;
    private String address;
    private String email;
    private String genderCd;
    private String phone;
    private Timestamp regDt;
    private String regId;
    private Timestamp modDt;
    private String modId;

    private String kakaoId;

    public static User createAdmin(UserDto userDto, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        switch (userDto.getRole()) {
            case SUPER_ADMIN:
                user.setRoleId(1);
                break;
            case ADMIN:
                user.setRoleId(2);
                break;
            default:
                break;
        }
        if (user.getRoleId() == null) {
            throw new IllegalStateException("적절하지 않은 관리자 권한입니다.");
        }
        user.setRegId(userDto.getRegAdmin());
        user.setGenderCd(userDto.getGenderCd());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setRegDt(new Timestamp(new Date().getTime()));
        user.setRegId(userDto.getRegAdmin());
        return user;
    }

    public static User createUser(UserDto userDto, PasswordEncoder passwordEncoder, HttpSession session) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setRegId(userDto.getName());
        user.setGenderCd(userDto.getGenderCd());
        user.setKakaoId((String) session.getAttribute("kakao_id"));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setRegDt(new Timestamp(new Date().getTime()));
        user.setRegId(userDto.getName());
        user.setRoleId(3);
        return user;
    }
}
