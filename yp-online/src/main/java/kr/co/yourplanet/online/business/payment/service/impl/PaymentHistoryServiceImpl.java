package kr.co.yourplanet.online.business.payment.service.impl;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.entity.payment.PaymentHistory;
import kr.co.yourplanet.core.entity.payment.PaymentStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.payment.repository.PaymentHistoryRepository;
import kr.co.yourplanet.online.business.payment.service.PaymentHistoryService;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    @Override
    public void save(PaymentHistory paymentHistory) {
        paymentHistoryRepository.save(paymentHistory);
    }

    @Override
    public void saveSuccessHistory(PaymentResponse response, long projectId) {
        PaymentResponse.SuccessResponse successResponse = response.getSuccessResponse();

        PaymentHistory history = PaymentHistory.builder()
                .projectId(projectId)
                .paymentKey(response.getPaymentKey())
                .orderId(response.getOrderId())
                .orderName(successResponse.getOrderName())
                .status(PaymentStatus.DONE)
                .providerStatus(successResponse.getStatus())
                .method(successResponse.getMethod())
                .totalAmount(successResponse.getTotalAmount())
                .provider(response.getProvider())
                .providerResponse(response.getProviderResponse())
                .build();

        save(history);
    }

    @Override
    public void saveFailHistory(PaymentResponse response, long projectId) {
        PaymentResponse.FailResponse failResponse = response.getFailResponse();

        PaymentHistory history = PaymentHistory.builder()
                .projectId(projectId)
                .paymentKey(response.getPaymentKey())
                .orderId(response.getOrderId())
                .status(PaymentStatus.ABORTED)
                .reason(failResponse.getMessage())
                .provider(response.getProvider())
                .providerResponse(response.getProviderResponse())
                .build();

        save(history);
    }

    @Override
    public void checkIfExists(String orderId) {
        if (paymentHistoryRepository.existsByOrderId(orderId)) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 처리된 주문 번호 입니다.", true);
        }
    }
}