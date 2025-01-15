package kr.co.yourplanet.online.business.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.yourplanet.core.entity.payment.PaymentHistory;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Long> {
}
