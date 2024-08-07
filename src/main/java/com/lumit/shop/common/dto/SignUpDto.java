package com.lumit.shop.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.lang.reflect.Field;
import java.util.*;

@Getter
@Setter
public class SignUpDto {
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

    public List<String> getVariables() {
        List<String> variableNames = new ArrayList<>();
        List<Field> fields = Arrays.stream(getFields()).toList();
        for (int i = 0; i < fields.size(); i++) {
            variableNames.add(fields.get(i).getName());
        }
        return variableNames;
    }


    private Field[] getFields() {
        return this.getClass().getDeclaredFields();
    }
}
