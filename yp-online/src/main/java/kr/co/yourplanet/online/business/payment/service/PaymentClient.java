package kr.co.yourplanet.online.business.payment.service;

import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;

public interface PaymentClient {

    PaymentResponse process(PaymentRequest request, String idempotencyKey);
}
