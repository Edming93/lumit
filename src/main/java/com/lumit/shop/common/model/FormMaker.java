package com.lumit.shop.common.model;

import com.lumit.shop.common.constants.InputType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormMaker {
    private String inputType;
    private String label;
    private String name;
    private String placeholder;

    public FormMaker(String name) {
        this.name = name;
        this.inputType = getInputType(name);
        this.placeholder = getPlaceholder(name);
        this.label = getLabel(name);
    }

    private String getInputType(String name) {
        InputType result;
        switch (name) {
            case "password":
                result = InputType.PASSWORD;
                break;
            case "genderCd":
                result = InputType.RADIO;
                break;
            case "email":
                result = InputType.EMAIL;
                break;
            default:
                result = InputType.TEXT;
                break;
        }
        return result.label();
    }

    private String getPlaceholder(String name) {
        String result = "";
        switch (name) {
            case "phone":
                result = "전화번호를 ";
                break;
            case "password":
                result = "비밀번호를 ";
                break;
            case "email":
                result = "이메일을 ";
                break;
            case "address":
                result = "주소를 ";
                break;
            case "name":
                result = "이름을 ";
                break;
            case "userId":
                result = "아이디를 ";
                break;
            default:
                break;
        }
        return result + "입력해주세요.";
    }

    public String getLabel(String name) {
        String result = "";
        switch (name) {
            case "phone":
                result = "전화번호";
                break;
            case "password":
                result = "비밀번호";
                break;
            case "email":
                result = "이메일";
                break;
            case "address":
                result = "주소";
                break;
            case "name":
                result = "이름";
                break;
            case "userId":
                result = "아이디";
                break;
            default:
                break;
        }
        return result;
    }
}
