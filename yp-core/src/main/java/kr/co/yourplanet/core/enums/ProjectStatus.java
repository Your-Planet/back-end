package kr.co.yourplanet.core.enums;

import lombok.Getter;

@Getter
public enum ProjectStatus {

    DEFAULT("기본", 0),
    REQUEST("의뢰", 72),
    NEGOTIATE_SPONSOR("광고주협상", 72),
    NEGOTIATE_CREATOR("작가협상", 72),
    CANCEL("취소", 72),
    REJECT("거절", 72),
    ACCEPT("수락", 72),
    COMPLETE("완료", 72);

    private final String statusName;
    private final int expirationHours;

    private ProjectStatus(String statusName, int expirationHours){
        this.statusName = statusName;
        this.expirationHours = expirationHours;
    }

}
