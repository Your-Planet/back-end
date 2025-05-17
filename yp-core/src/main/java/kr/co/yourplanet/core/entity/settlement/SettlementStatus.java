package kr.co.yourplanet.core.entity.settlement;

import lombok.Getter;

@Getter
public enum SettlementStatus {

    SETTLEMENT_PENDING("정산 대기"),
    SETTLEMENT_READY("정산 가능"),
    REVIEW_REQUIRED("확인 필요"),
    SETTLEMENT_COMPLETED("정산 완료")
    ;

    private final String name;

    SettlementStatus(String name) {
        this.name = name;
    }
}
