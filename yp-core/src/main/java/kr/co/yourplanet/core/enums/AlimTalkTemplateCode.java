package kr.co.yourplanet.core.enums;

import lombok.Getter;

@Getter
public enum AlimTalkTemplateCode {

    VERIFICATION_CODE("YP_ALIM_0001", "인증번호"),
    MEMBER_JOIN_CREATOR("YP_ALIM_0002", "회원가입 완료 작가"),
    MEMBER_JOIN_SPONSOR("YP_ALIM_0003", "회원가입 완료 광고주");

    private final String code;
    private final String description;

    AlimTalkTemplateCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
