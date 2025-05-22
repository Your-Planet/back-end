package kr.co.yourplanet.online.business.payment.service;

import kr.co.yourplanet.core.entity.payment.PaymentHistory;
import kr.co.yourplanet.core.entity.payment.PaymentType;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;

public interface PaymentHistoryService {

    void save(PaymentHistory paymentHistory);

    void saveSuccessHistory(PaymentResponse response, PaymentType paymentType, Long targetId);

    void saveFailHistory(PaymentResponse response, PaymentType paymentType, Long targetId);

    void checkIfExists(String orderId);
}
