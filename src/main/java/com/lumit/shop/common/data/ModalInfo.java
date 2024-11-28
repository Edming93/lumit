package com.lumit.shop.common.data;

import com.lumit.shop.common.constants.ServiceCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModalInfo {
    private String title;
    private String content;
    private String choice;
    private String name;
    private String type;
    private ServiceCode sc;

    public enum Type {
        SIMPLE, PROMPT, LIST
    }

    public enum Title {
        USERINFO("회원 정보 변경"), FIND_ID("아이디 찾기"), NICK("닉네임 변경"), PASSWORD("비밀번호 변경"), PHONE("휴대폰 번호 변경"), SEND_EMAIL("이메일 주소 인증"), EMAIL("이메일 주소 변경"), ADDRESS("배송지 주소 변경"), ACCOUNT("환불 계좌 변경"), TEMP_PASSWORD("임시 비밀번호 발급");

        public final String label;

        private Title(String label) {
            this.label = label;
        }
    }

    public ModalInfo(Title t, ServiceCode sc) {
        this.title = t.label;
        this.sc = sc;
        this.type = "simple";
        this.choice = "";
        if (sc.equals(ServiceCode.UNKNOWN)) {
            this.content = "정보를 수정하지 못하였습니다.<br>잠시 후 다시 시도해 주세요.";
            this.name = "userInfoError";
        }
        switch (t) {
            case USERINFO:
                if (sc.equals(ServiceCode.UNAUTHORIZED)) {
                    this.content = "정보 수정의 권한이 없습니다.";
                    this.name = "forbiddenError";
                }
                break;
            case FIND_ID:
                if (sc.equals(ServiceCode.NOT_FOUND)) {
                    this.content = "입력하신 이메일 주소로 가입된 회원이 없습니다.";
                    this.name = "userNotExists";
                }
            case TEMP_PASSWORD:
                if (sc.equals(ServiceCode.NOT_FOUND)) {
                    this.content = "존재하지 않는 회원이거나 이메일 주소가 틀렸습니다.";
                    this.name = "userNotMatched";
                }
            case NICK:
                if (sc.equals(ServiceCode.NOT_MODIFIED)) {
                    this.content = "변경할 정보가 없습니다.";
                    this.name = "nickNotChanged";
                } else if (sc.equals(ServiceCode.UPDATED)) {
                    this.content = "닉네임이 변경되었습니다.";
                    this.name = "nickChanged";
                }
                break;
            case PASSWORD:
                if (sc.equals(ServiceCode.UNAUTHORIZED)) {
                    this.content = "비밀번호가 틀렸습니다.<br>확인 후 다시 시도해주세요.";
                    this.name = "wrongPassword";
                } else if (sc.equals(ServiceCode.UPDATED)) {
                    this.content = "비밀번호가 수정되었습니다.";
                    this.name = "passwordUpdated";
                }
                break;
            case SEND_EMAIL:
                if (sc.equals(ServiceCode.CONFLICT)) {
                    this.content = "다른 계정에 이미 사용되고 있는 주소입니다.";
                    this.name = "emailConflict";
                } else if (sc.equals(ServiceCode.NOT_MODIFIED)) {
                    this.content = "현재 이메일 주소와 동일하여<br>변경할 정보가 없습니다.";
                    this.name = "emailNotModified";
                } else if (sc.equals(ServiceCode.UNKNOWN)) {
                    this.content = "이메일 전송에 실패하였습니다.<br>잠시 후 다시 시도해주세요.";
                    this.name = "emailSendingFail";
                } else {
                    this.content = "이메일이 전송되었습니다.<br>메일함을 확인해주세요.";
                    this.name = "emailSendingSuccess";
                }
                break;
            case EMAIL:
                if (!sc.equals(ServiceCode.UPDATED)) {
                    this.content = "이메일을 변경하지 못하였습니다.<br>잠시 후 다시 시도해주세요.";
                    this.name = "emailFail";
                } else {
                    this.content = "이메일 주소가 변경되었습니다.";
                    this.name = "emailSuccess";
                }
                break;
            /**
             * todo
             *
             * 나머지 케이스에 대해서도 구현하기.
             */
        }
    }
};

