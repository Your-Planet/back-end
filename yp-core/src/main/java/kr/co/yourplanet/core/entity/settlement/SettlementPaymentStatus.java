package kr.co.yourplanet.core.entity.settlement;

import lombok.Getter;

@Getter
public enum SettlementPaymentStatus {

    PAYMENT_PENDING("결제 대기"),
    PAYMENT_COMPLETED("결제 완료")
    ;

    private final String name;

    SettlementPaymentStatus(String name) {
        this.name = name;
    }
}
