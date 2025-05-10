package kr.co.yourplanet.online.business.settlement.repository.impl;

import static kr.co.yourplanet.core.entity.settlement.QProjectSettlement.*;

import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;
import kr.co.yourplanet.online.business.settlement.repository.ProjectSettlementRepositoryCustom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectSettlementRepositoryCustomImpl implements ProjectSettlementRepositoryCustom {

    private final JPAQueryFactory query;

    @Override
    public long countByStatus(long memberId, SettlementPaymentStatus paymentStatus, SettlementStatus settlementStatus) {
        return Optional.ofNullable(
                query.select(projectSettlement.count())
                        .from(projectSettlement)
                        .where(
                                isRelatedToMemberId(memberId),
                                paymentStatusEq(paymentStatus),
                                settlementStatusEq(settlementStatus)
                        )
                        .fetchOne()
        ).orElse(0L);
    }

    private BooleanExpression isRelatedToMemberId(long memberId) {
        return projectSettlement.project.creator.id.eq(memberId)
                .or(projectSettlement.project.sponsor.id.eq(memberId));
    }

    private BooleanExpression paymentStatusEq(SettlementPaymentStatus paymentStatus) {
        return paymentStatus != null ? projectSettlement.paymentStatus.eq(paymentStatus) : null;
    }

    private BooleanExpression settlementStatusEq(SettlementStatus settlementStatus) {
        return settlementStatus != null ? projectSettlement.settlementStatus.eq(settlementStatus) : null;
    }
}
