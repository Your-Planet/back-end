package kr.co.yourplanet.online.business.payment.service;

import kr.co.yourplanet.core.entity.payment.PaymentHistory;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;

public interface PaymentHistoryService {

    void save(PaymentHistory paymentHistory);

    void saveSuccessHistory(PaymentResponse response, long projectId);

    void saveFailHistory(PaymentResponse response, long projectId);

    void checkIfExists(String orderId);
}
