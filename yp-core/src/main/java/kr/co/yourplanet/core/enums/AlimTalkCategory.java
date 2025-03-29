package kr.co.yourplanet.core.enums;

import lombok.Getter;

@Getter
public enum AlimTalkCategory {
    SIGNUP("회원가입"),
    ACCOUNT("계정"),
    PROJECT("프로젝트"),
    CONTRACT("계약서"),
    SETTLEMENT("정산");

    private final String description;

    AlimTalkCategory(String description) {
        this.description = description;
    }
}