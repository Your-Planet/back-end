package kr.co.yourplanet.online.business.payment.service.processor;

import org.springframework.stereotype.Component;
import kr.co.yourplanet.core.entity.payment.PaymentType;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentProcessorFactory {

    private final ProjectPaymentProcessor projectPaymentProcessor;

    public PaymentProcessor getProcessor(PaymentType paymentType) {
        return switch (paymentType) {
            case PROJECT_SETTLEMENT -> projectPaymentProcessor;
        };
    }
}