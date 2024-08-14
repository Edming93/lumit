package com.lumit.shop.admin.dto;

import com.lumit.shop.admin.model.Role;
import com.lumit.shop.admin.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
//@ToString
public class UserDto extends User {
    private String regAdmin;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 입니다.")
    private String password;

    @NotBlank(message = "주소는 필수 입력 값입니다.")
    private String address;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "성별은 필수 입력 값입니다.")
    private String genderCd;

    @NotBlank(message = "전화번호는 필수 입력 값입니다.")
    private String phone;

//    @NotNull(message = "권한 설정은 필수 입력 값입니다.")
    private Role role;


}
