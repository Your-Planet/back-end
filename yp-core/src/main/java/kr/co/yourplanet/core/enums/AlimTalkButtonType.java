package kr.co.yourplanet.core.enums;

import lombok.Getter;

@Getter
public enum AlimTalkButtonType {

    WL("웹 링크"),
    AL("앱 링크"),
    BK("봇 키워드"),
    MD("메시지 전달"),
    DS("배송 조회"),
    BC("상담톡 전환"),
    BT("챗봇 전환"),
    AC("채널 추가"),
    P1("플러그인#1"),
    P2("플러그인#2"),
    P3("플러그인#3"),
    BF("비즈폼");

    private final String description;

    AlimTalkButtonType(String description) {
        this.description = description;
    }
}
