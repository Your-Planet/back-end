package kr.co.yourplanet.online.business.payment.service.impl;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.online.business.payment.domain.exception.PaymentFailureException;
import kr.co.yourplanet.online.business.payment.domain.exception.PaymentRequestNotFoundException;
import kr.co.yourplanet.online.business.payment.repository.PaymentRequestRepository;
import kr.co.yourplanet.online.business.payment.service.PaymentClient;
import kr.co.yourplanet.online.business.payment.service.PaymentHistoryService;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.business.payment.service.PaymentService;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.business.project.service.ProjectValidationService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRequestService paymentRequestService;
    private final PaymentHistoryService paymentHistoryService;
    private final ProjectValidationService projectValidationService;
    private final PaymentClient paymentClient;

    private final PaymentRequestRepository paymentRequestRepository;

    @Override
    public void approve(Long memberId, Long projectId, String paymentKey, String orderId, Long amount) {
        validatePaymentRequest(memberId, orderId, amount);
        projectValidationService.checkExist(projectId);

        String idempotencyKey = paymentRequestRepository.getIdempotencyKey(orderId)
                .orElseThrow(() -> new PaymentRequestNotFoundException("해당 주문의 동일성 보장 키가 없습니다."));
        PaymentRequest request = buildPaymentRequest(paymentKey, orderId, amount);
        PaymentResponse response = paymentClient.process(request, idempotencyKey);

        if (response.isSuccess()) {
            paymentHistoryService.saveSuccessHistory(response, projectId);
        } else {
            paymentHistoryService.saveFailHistory(response, projectId);

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