package kr.co.yourplanet.online.business.payment.service;

import kr.co.yourplanet.core.entity.payment.PaymentHistory;

public interface PaymentHistoryService {

    void save(PaymentHistory paymentHistory);

    void checkIfExists(String orderId);
}
