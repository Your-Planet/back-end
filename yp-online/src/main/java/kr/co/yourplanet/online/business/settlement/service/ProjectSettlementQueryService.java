package kr.co.yourplanet.online.business.settlement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.entity.settlement.ProjectSettlement;
import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;
import kr.co.yourplanet.core.entity.studio.Price;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.core.model.PageInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectBasicInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSettlementDetailInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSettlementSummariesInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSettlementSummaryInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSpecInfo;
import kr.co.yourplanet.online.business.settlement.dto.SettlementInfo;
import kr.co.yourplanet.online.business.settlement.dto.SponsorInfo;
import kr.co.yourplanet.online.business.settlement.repository.ProjectSettlementRepository;
import kr.co.yourplanet.online.business.user.service.MemberValidationService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProjectSettlementQueryService {

    private final MemberValidationService memberValidationService;

    private final ProjectSettlementRepository projectSettlementRepository;

    public ProjectSettlement getByProjectId(long projectId) {
        return projectSettlementRepository.findByProjectId(projectId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "요청한 정산 정보를 찾을 수 없습니다.", true));
    }

    public long countByStatus(long requesterId, SettlementPaymentStatus paymentStatus,
            SettlementStatus settlementStatus) {
        memberValidationService.validateIsCreator(requesterId);

        return projectSettlementRepository.countByStatus(requesterId, paymentStatus, settlementStatus);
    }

    public ProjectSettlementSummariesInfo getSummariesInfo(Pageable pageable) {
        Page<ProjectSettlement> projectSettlements = projectSettlementRepository.findAll(pageable);
        List<ProjectSettlementSummaryInfo> summaries = projectSettlements.stream()
                .map(this::createSummaryInfo)
                .toList();

        PageInfo pageInfo = PageInfo.from(projectSettlements);

        return ProjectSettlementSummariesInfo.builder()
                .projectSettlementSummaryInfo(summaries)
                .pageInfo(pageInfo)
                .build();
    }

    public ProjectSettlementDetailInfo getDetailInfo(long projectId) {
        ProjectSettlement settlement = getByProjectId(projectId);

        return ProjectSettlementDetailInfo.builder()
                .projectBasicInfo(toProjectBasicInfo(settlement.getProject()))
                .sponsorInfo(toSponsorInfo(settlement.getProject()))
                .settlementInfo(toSettlementInfo(settlement))
                .projectSpecInfo(toProjectSpecInfo(settlement.getProject()))
                .build();
    }

    private ProjectSettlementSummaryInfo createSummaryInfo(ProjectSettlement settlement) {
        return ProjectSettlementSummaryInfo.builder()
                .projectBasicInfo(toProjectBasicInfo(settlement.getProject()))
                .sponsorInfo(toSponsorInfo(settlement.getProject()))
                .settlementInfo(toSettlementInfo(settlement))
                .build();
    }

    private ProjectBasicInfo toProjectBasicInfo(Project project) {
        return ProjectBasicInfo.builder()
                .orderCode(project.getOrderCode())
                .orderTitle(project.getOrderTitle())
                .build();
    }

    private SponsorInfo toSponsorInfo(Project project) {
        return SponsorInfo.builder()
                .name(project.getBrandName())
                .email(project.getSponsor().getEmail())
                .build();
    }

    private SettlementInfo toSettlementInfo(ProjectSettlement settlement) {
        return SettlementInfo.builder()
                .paymentAmount(settlement.getPaymentAmount())
                .settlementAmount(settlement.getSettlementAmount())
                .paymentCompletedAt(settlement.getPaymentDate())
                .contractCompletedAt(settlement.getContractDate())
                .build();
    }

    private ProjectSpecInfo toProjectSpecInfo(Project project) {
        ProjectHistory history = project.getAcceptedHistory()
                .orElseThrow(() -> new BusinessException(StatusCode.CONFLICT, "수락된 프로젝트가 아닙니다.", false));
        Price price = project.getCreatorPrice();

        return ProjectSpecInfo.builder()
                .dueDate(history.getDueDate())
                .cutCount(price.getCuts())
                .additionalCutCount(history.getAdditionalPanelCount())
                .finalCutCount(price.getCuts() + history.getAdditionalPanelCount())
                .modificationCount(price.getModificationCount())
                .additionalModificationCount(history.getAdditionalModificationCount())
                .finalModificationCount(price.getModificationCount() + history.getAdditionalModificationCount())
                .build();
    }
}
