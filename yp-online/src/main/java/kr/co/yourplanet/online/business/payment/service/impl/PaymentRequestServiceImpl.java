package kr.co.yourplanet.online.business.payment.service.impl;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.payment.repository.PaymentRequestRepository;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentRequestServiceImpl implements PaymentRequestService {

    private final PaymentRequestRepository paymentRequestRepository;

    @Override
    public void savePaymentRequest(Long memberId, String orderId, Long amount) {
        // TODO: 결제 내역 구현 후 같은 주문 번호 존재 확인
        if(isPaymentRequestExist(orderId)) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 존재하는 결제 요청입니다.", true);
        }

        paymentRequestRepository.save(memberId, orderId, amount);
    }

    private boolean isPaymentRequestExist(String orderId) {
        return paymentRequestRepository.isExist(orderId);
    }
}
