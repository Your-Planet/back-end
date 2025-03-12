package kr.co.yourplanet.core.enums;

import lombok.Getter;

@Getter
public enum AlimTalkMsgType {

    AT("알림톡"),
    AI("알림톡 이미지"),
    FT("친구톡"),
    FI("친구톡 이미지"),
    FW("친구톡 와이드 이미지");

    private final String description;

    AlimTalkMsgType(String description) {
        this.description = description;
    }
}
