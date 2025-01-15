package kr.co.yourplanet.online.business.payment.domain.exception;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.Getter;

@Getter
public class PaymentFailureException extends BusinessException {
    private final String paymentKey;
    private final String orderId;

    public PaymentFailureException(String message, String paymentKey, String orderId) {
        super(StatusCode.UNPROCESSABLE_ENTITY, message, true);
        this.paymentKey = paymentKey;
        this.orderId = orderId;
    }
}
