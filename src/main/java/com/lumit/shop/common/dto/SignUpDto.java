package com.lumit.shop.common.dto;

import com.lumit.shop.common.model.TbLogin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class SignUpDto {
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userId;

    private int roleId;


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

    private String socialId;
    private Boolean defaultDeliveryAddr;

    private static ModelMapper modelMapper = new ModelMapper();

    public TbLogin createTbLogin() {
        return modelMapper.map(this, TbLogin.class);
    }

    public static SignUpDto of(TbLogin tbLogin) {
        return modelMapper.map(tbLogin, SignUpDto.class);
    }
}
