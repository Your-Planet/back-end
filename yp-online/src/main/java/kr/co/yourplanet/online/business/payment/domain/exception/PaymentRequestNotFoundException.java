package kr.co.yourplanet.online.business.payment.domain.exception;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;

public class PaymentRequestNotFoundException extends BusinessException {

    public PaymentRequestNotFoundException(String message) {
        super(StatusCode.NOT_FOUND, message, true);
    }
}
