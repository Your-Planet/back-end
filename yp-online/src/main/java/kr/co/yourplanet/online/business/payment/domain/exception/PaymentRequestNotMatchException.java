package kr.co.yourplanet.online.business.payment.domain.exception;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;

public class PaymentRequestNotMatchException extends BusinessException {

    public PaymentRequestNotMatchException(String message) {
        super(StatusCode.CONFLICT, message, true);
    }
}
