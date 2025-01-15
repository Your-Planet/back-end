package kr.co.yourplanet.online.business.payment.service.impl;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.entity.payment.PaymentHistory;
import kr.co.yourplanet.online.business.payment.repository.PaymentHistoryRepository;
import kr.co.yourplanet.online.business.payment.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentHistoryServiceImpl implements PaymentHistoryService {

    private final PaymentHistoryRepository paymentHistoryRepository;

    @Override
    public void save(PaymentHistory paymentHistory) {
        paymentHistoryRepository.save(paymentHistory);
    }
}