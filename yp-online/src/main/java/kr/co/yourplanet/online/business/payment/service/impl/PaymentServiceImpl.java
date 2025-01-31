package kr.co.yourplanet.online.business.payment.service.impl;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.online.business.payment.domain.exception.PaymentFailureException;
import kr.co.yourplanet.online.business.payment.repository.PaymentRequestRepository;
import kr.co.yourplanet.online.business.payment.service.PaymentClient;
import kr.co.yourplanet.online.business.payment.service.PaymentHistoryService;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.business.payment.service.PaymentService;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRequestService paymentRequestService;
    private final PaymentHistoryService paymentHistoryService;
    private final PaymentClient paymentClient;

    private final PaymentRequestRepository paymentRequestRepository;

    @Override
    public void approve(Long memberId, String paymentKey, String orderId, Long amount) {
        validatePaymentRequest(memberId, orderId, amount);

        String idempotencyKey = paymentRequestRepository.getIdempotencyKey(orderId);
        PaymentRequest request = buildPaymentRequest(paymentKey, orderId, amount);
        PaymentResponse response = paymentClient.process(request, idempotencyKey);

        if (response.isSuccess()) {
            paymentHistoryService.saveSuccessHistory(response);
        } else {
            paymentHistoryService.saveFailHistory(response);

            PaymentResponse.FailResponse failResponse = response.getFailResponse();
            throw new PaymentFailureException(failResponse.getMessage());
        }
    }

    private void validatePaymentRequest(Long memberId, String orderId, Long amount) {
        paymentHistoryService.checkIfExists(orderId);
        paymentRequestService.checkIfNotExists(orderId);
        paymentRequestService.checkIfOrderMatches(orderId, memberId);
        paymentRequestService.checkIfAmountMatches(orderId, amount);
    }

    private PaymentRequest buildPaymentRequest(String paymentKey, String orderId, Long amount) {
        return PaymentRequest.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .amount(amount)
                .build();
    }
}