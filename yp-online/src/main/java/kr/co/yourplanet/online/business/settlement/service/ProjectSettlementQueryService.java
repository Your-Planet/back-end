package kr.co.yourplanet.online.business.settlement.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.settlement.ProjectSettlement;
import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.core.model.PageInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectBasicInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSettlementSummariesInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSettlementSummaryInfo;
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

    private ProjectSettlementSummaryInfo createSummaryInfo(ProjectSettlement settlement) {
        Project project = settlement.getProject();
        Member sponsor = project.getSponsor();

        ProjectBasicInfo projectBasicInfo = ProjectBasicInfo.builder()
                .orderCode(project.getOrderCode())
                .orderTitle(project.getOrderTitle())
                .build();

        SponsorInfo sponsorInfo = SponsorInfo.builder()
                .name(project.getBrandName())
                .email(sponsor.getEmail())
                .build();

        SettlementInfo settlementInfo = SettlementInfo.builder()
                .paymentAmount(settlement.getPaymentAmount())
                .settlementAmount(settlement.getSettlementAmount())
                .paymentCompletedAt(settlement.getPaymentDate())
                .contractCompletedAt(settlement.getContractDate())
                .build();

        return ProjectSettlementSummaryInfo.builder()
                .sponsorInfo(sponsorInfo)
                .projectBasicInfo(projectBasicInfo)
                .settlementInfo(settlementInfo)
                .build();
    }
}
