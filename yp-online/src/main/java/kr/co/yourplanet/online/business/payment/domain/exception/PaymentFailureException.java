package kr.co.yourplanet.online.business.payment.domain.exception;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.Getter;

@Getter
public class PaymentFailureException extends BusinessException {
    private final String reason;

    public PaymentFailureException(String reason) {
        super(StatusCode.UNPROCESSABLE_ENTITY, reason, true);
        this.reason = reason;
    }
}
