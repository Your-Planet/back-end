package kr.co.yourplanet.online.business.settlement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kr.co.yourplanet.core.entity.settlement.ProjectSettlement;
import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;

public interface ProjectSettlementRepositoryCustom {

    long countByStatus(long memberId, SettlementPaymentStatus paymentStatus, SettlementStatus settlementStatus);

    Page<ProjectSettlement> findAll(Pageable pageable);
}
