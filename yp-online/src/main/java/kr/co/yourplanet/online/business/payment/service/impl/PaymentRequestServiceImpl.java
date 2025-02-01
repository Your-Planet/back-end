package kr.co.yourplanet.online.business.payment.service.impl;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.payment.domain.exception.PaymentRequestNotFoundException;
import kr.co.yourplanet.online.business.payment.domain.exception.PaymentRequestNotMatchException;
import kr.co.yourplanet.online.business.payment.repository.PaymentRequestRepository;
import kr.co.yourplanet.online.business.payment.service.PaymentHistoryService;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentRequestServiceImpl implements PaymentRequestService {

    private final PaymentHistoryService paymentHistoryService;

    private final PaymentRequestRepository paymentRequestRepository;

    @Override
    public void save(Long memberId, String orderId, Long amount) {
        paymentHistoryService.checkIfExists(orderId);
        this.checkIfExists(orderId);

        paymentRequestRepository.save(memberId, orderId, amount);
    }

    @Override
    public void checkIfExists(String orderId) {
        if (paymentRequestRepository.isExist(orderId)) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 존재하는 결제 요청입니다.", true);
        }
    }

    @Override
    public void checkIfNotExists(String orderId) {
        if (!paymentRequestRepository.isExist(orderId)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "만료되거나 존재하지 않는 결제 요청입니다.", true);
        }
    }

    @Override
    public void checkIfOrdererMatches(String orderId, Long memberId) {
        Long ordererId = paymentRequestRepository.getOrdererId(orderId)
                .orElseThrow(() -> new PaymentRequestNotFoundException("해당 주문의 주문자 정보가 없습니다."));

        if (!ordererId.equals(memberId)) {
            throw new PaymentRequestNotMatchException("결제 요청자가 일치하지 않습니다.");
        }
    }

    @Override
    public void checkIfAmountMatches(String orderId, Long amount) {
        Long currentAmount = paymentRequestRepository.getAmount(orderId)
                .orElseThrow(() -> new PaymentRequestNotFoundException("해당 주문의 금액 정보가 없습니다."));

        if (!amount.equals(currentAmount)) {
            throw new PaymentRequestNotMatchException("주문 정보가 일치하지 않습니다.");
        }
    }
}
