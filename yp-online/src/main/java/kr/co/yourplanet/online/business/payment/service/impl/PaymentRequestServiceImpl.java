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
    public void save(Long memberId, String orderId, Long amount) {
        // TODO: 결제 내역 구현 후 같은 주문 번호 존재 확인
        if(paymentRequestRepository.isExist(orderId)) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 존재하는 결제 요청입니다.", true);
        }

        paymentRequestRepository.save(memberId, orderId, amount);
    }

    @Override
    public void checkIfExists(String orderId) {
        if (!paymentRequestRepository.isExist(orderId)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "만료되거나 존재하지 않는 결제 요청입니다.", true);
        }
    }

    @Override
    public void checkIfOrderMatches(String orderId, Long memberId) {
        Long ordererId = paymentRequestRepository.getOrdererId(orderId);
        if (!ordererId.equals(memberId)) {
            throw new BusinessException(StatusCode.CONFLICT, "결제 요청자가 일치하지 않습니다.", true);
        }
    }

    @Override
    public void checkIfAmountMatches(String orderId, Long amount) {
        Long currentAmount = paymentRequestRepository.getAmount(orderId);
        if (!amount.equals(currentAmount)) {
            throw new BusinessException(StatusCode.CONFLICT, "주문 정보가 일치하지 않습니다.", true);
        }
    }
}
