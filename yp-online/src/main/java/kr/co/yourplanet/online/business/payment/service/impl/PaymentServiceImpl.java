package kr.co.yourplanet.online.business.payment.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.payment.PaymentType;
import kr.co.yourplanet.online.business.payment.domain.exception.PaymentFailureException;
import kr.co.yourplanet.online.business.payment.domain.exception.PaymentRequestNotFoundException;
import kr.co.yourplanet.online.business.payment.repository.PaymentRequestRepository;
import kr.co.yourplanet.online.business.payment.service.PaymentClient;
import kr.co.yourplanet.online.business.payment.service.PaymentHistoryService;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.business.payment.service.PaymentService;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.business.payment.service.processor.PaymentProcessor;
import kr.co.yourplanet.online.business.payment.service.processor.PaymentProcessorFactory;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRequestService paymentRequestService;
    private final PaymentHistoryService paymentHistoryService;
    private final PaymentProcessorFactory paymentProcessorFactory;
    private final PaymentClient paymentClient;

    private final PaymentRequestRepository paymentRequestRepository;

    @Override
    public void approve(PaymentType paymentType, Long memberId, String paymentKey, String orderId, Long amount, Long targetId) {
        validatePaymentRequest(memberId, orderId, amount);

        PaymentProcessor processor = paymentProcessorFactory.getProcessor(paymentType);
        processor.validate(targetId, memberId);

        String idempotencyKey = paymentRequestRepository.getIdempotencyKey(orderId)
                .orElseThrow(() -> new PaymentRequestNotFoundException("해당 주문의 동일성 보장 키가 없습니다."));
        PaymentRequest request = buildPaymentRequest(paymentKey, orderId, amount);
        PaymentResponse response = paymentClient.process(request, idempotencyKey);

        if (response.isSuccess()) {
            paymentHistoryService.saveSuccessHistory(response, paymentType, targetId);
            processor.afterPayment(response, targetId);
        } else {
            paymentHistoryService.saveFailHistory(response, paymentType, targetId);
            PaymentResponse.FailResponse failResponse = response.getFailResponse();
            throw new PaymentFailureException(failResponse.getMessage());
        }
    }

    private void validatePaymentRequest(Long memberId, String orderId, Long amount) {
        paymentHistoryService.checkIfExists(orderId);
        paymentRequestService.checkIfNotExists(orderId);
        paymentRequestService.checkIfOrdererMatches(orderId, memberId);
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