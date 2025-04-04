package kr.co.yourplanet.core.enums;

import lombok.Getter;

@Getter
public enum PostDurationMonthType {
    ONE_MONTH("1"),
    TWO_MONTH("2"),
    THREE_MONTH("3"),
    SIX_MONTH("6"),
    MORE_THAN_ONE_YEAR("12+")
    ;

    private final String value;

    PostDurationMonthType(String value) {
        this.value = value;
    }
}
