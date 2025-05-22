package kr.co.yourplanet.online.business.settlement.repository.impl;

import static kr.co.yourplanet.core.entity.settlement.QProjectSettlement.*;
import static kr.co.yourplanet.core.entity.project.QProject.*;
import static kr.co.yourplanet.core.entity.member.QMember.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import kr.co.yourplanet.core.entity.settlement.ProjectSettlement;
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

    @Override
    public Page<ProjectSettlement> findAll(Pageable pageable) {
        List<ProjectSettlement> content = query.select(projectSettlement)
                .from(projectSettlement)
                .join(projectSettlement.project, project).fetchJoin()
                .join(project.sponsor, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(
                query.select(projectSettlement.count())
                        .from(projectSettlement)
                        .fetchOne())
                .orElse(0L);

        return new PageImpl<>(content, pageable, total);
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
