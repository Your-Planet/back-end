package kr.co.yourplanet.online.business.settlement.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.entity.settlement.ProjectSettlement;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.service.ProjectQueryService;
import kr.co.yourplanet.online.business.project.service.ProjectValidationService;
import kr.co.yourplanet.online.business.settlement.repository.ProjectSettlementRepository;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProjectSettlementService {

    private final MemberQueryService memberQueryService;
    private final ProjectQueryService projectQueryService;
    private final ProjectValidationService projectValidationService;
    private final ProjectSettlementQueryService projectSettlementQueryService;

    private final ProjectSettlementRepository projectSettlementRepository;

    // 프로젝트 수락 시 호출
    public void create(long sponsorId, long projectId) {
        projectValidationService.checkSponsor(projectId, sponsorId);
        projectValidationService.checkInProgress(projectId);

        Member sponsor = memberQueryService.getById(sponsorId);
        Project project = projectQueryService.getById(projectId);
        ProjectHistory history = project.getAcceptedHistory()
                .orElseThrow(() -> new BusinessException(StatusCode.CONFLICT, "수락된 프로젝트가 아닙니다.", false));

        long paymentAmount = history.getOfferPrice();

        ProjectSettlement settlement = ProjectSettlement.create(sponsor, project, paymentAmount);
        projectSettlementRepository.save(settlement);
    }

    // 결제 완료 시 호출
    public void markPaymentCompleted(Long projectId, LocalDateTime approvedAt) {
        ProjectSettlement settlement = projectSettlementQueryService.getByProjectId(projectId);

        if (settlement.isSettlementCompleted()) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 완료된 정산 건을 변경할 수 없습니다.", false);
        }

        settlement.completePayment(approvedAt);
        projectSettlementRepository.save(settlement);
    }
}
