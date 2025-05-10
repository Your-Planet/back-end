package kr.co.yourplanet.online.business.settlement.repository;

import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;

public interface ProjectSettlementRepositoryCustom {

    long countByStatus(long memberId, SettlementPaymentStatus paymentStatus, SettlementStatus settlementStatus);
}
