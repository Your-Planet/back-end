package kr.co.yourplanet.online.business.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.BusinessInfo;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.SettlementInfo;
import kr.co.yourplanet.core.entity.project.Contractor;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectContract;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.entity.studio.Price;
import kr.co.yourplanet.core.enums.DemandType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.dto.request.ContractDraftForm;
import kr.co.yourplanet.online.business.project.dto.response.ContractInfo;
import kr.co.yourplanet.online.business.settlement.service.ProjectSettlementService;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ContractDraftService {

    private final ProjectQueryService projectQueryService;
    private final ContractService contractService;
    private final ContractValidationService contractValidationService;
    private final MemberQueryService memberQueryService;
    private final ProjectSettlementService projectSettlementService;

    /**
     * 계약서 조회 및 DB에 기본 계약서 저장
     */
    public ContractInfo getContract(Long projectId, Long memberId) {
        Project project = projectQueryService.getById(projectId);
        ProjectHistory history = project.getAcceptedHistory()
                .orElseThrow(() -> new BusinessException(StatusCode.CONFLICT, "수락된 프로젝트가 아닙니다.", false));

        contractValidationService.validateContractParty(memberId, project);
        ProjectContract contract = getOrCreateProjectContract(project, history);

        return createContractInfo(project, history, contract);
    }

    /**
     * 계약서 작성
     */
    public void draftContract(Long projectId, Long memberId, ContractDraftForm form) {
        validateProjectForContract(projectId, memberId);
        
        ProjectContract contract = getUncompletedContract(projectId);
        Member member = memberQueryService.getById(memberId);
        Contractor contractor = createContractor(form);

        writeContractInfo(contract, member.getMemberType(), contractor);

        completeIfDone(contract);
        contractService.save(contract);

        // 정산 정보에 계약 완료 시간 기록
        projectSettlementService.markContractCompleted(projectId, contract.getCompleteDateTime());
    }

    private void validateProjectForContract(Long projectId, Long memberId) {
        Project project = projectQueryService.getById(projectId);
        contractValidationService.validateContractParty(memberId, project);
        contractValidationService.validateProjectStatus(project.getProjectStatus());
    }

    private ProjectContract getUncompletedContract(Long projectId) {
        ProjectContract contract = contractService.getByProjectId(projectId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "계약서가 존재하지 않습니다.", false));
        contractValidationService.validateContractCompleted(contract);
        
        return contract;
    }

    private void writeContractInfo(ProjectContract contract, MemberType memberType, Contractor contractor) {
        contractValidationService.validateAlreadyWritten(memberType, contract);

        // 수요자/공급자 정보 입력
        if (MemberType.CREATOR.equals(memberType)) {
            contract.writeProviderInfo(contractor);
        } else if (MemberType.SPONSOR.equals(memberType)) {
            contract.writeClientInfo(contractor);
        }
    }

    private void completeIfDone(ProjectContract contract) {
        // 계약 완료 시간 기록
        if (contract.isCompleted()) {
            contract.completeContract();
        }
    }

    private ProjectContract getOrCreateProjectContract(Project project, ProjectHistory history) {
        return contractService.getByProjectId(project.getId())
                .orElseGet(() -> {
                    ProjectContract newContract = ProjectContract.builder()
                            .project(project)
                            .acceptDateTime(project.getAcceptDateTime())
                            .completeDateTime(project.getCompleteDateTime())
                            .contractAmount(history.getOfferPrice().longValue())
                            .provider(createTempContractor(project.getCreator()))
                            .client(createTempContractor(project.getSponsor()))
                            .build();
                    contractService.save(newContract);
                    return newContract;
                });
    }

    private Contractor createTempContractor(Member member) {
        switch (member.getBusinessType()) {
            case BUSINESS -> {
                BusinessInfo businessInfo = member.getBusinessInfo();

                return Contractor.builder()
                        .name(businessInfo.getCompanyName())
                        .registrationNumber(businessInfo.getBusinessNumber())
                        .address(businessInfo.getBusinessAddress())
                        .representativeName(businessInfo.getRepresentativeName())
                        .build();
            }
            
            case INDIVIDUAL -> {
                SettlementInfo settlementInfo = member.getSettlementInfo();

                return Contractor.builder()
                        .name(member.getName())
                        .registrationNumber(member.hasSettlementInfo() ? settlementInfo.getRrn() : null)
                        .address(null)
                        .representativeName(null)
                        .build();
            }
            
            default -> throw new BusinessException(StatusCode.CONFLICT, "계약서를 작성할 수 없는 멤버 타입입니다.", false);
        }
    }

    private Contractor createContractor(ContractDraftForm form) {
        return Contractor.builder()
                .name(form.companyName())
                .registrationNumber(form.identificationNumber())
                .address(form.address())
                .representativeName(form.representativeName())
                .build();
    }

    private ContractInfo createContractInfo(Project project, ProjectHistory history, ProjectContract contract) {
        Price price = project.getCreatorPrice();

        return ContractInfo.builder()
                .projectId(project.getId())
                .projectName(project.getOrderTitle())
                .acceptDateTime(contract.getAcceptDateTime())
                .completeDateTime(contract.getDeadline())
                .contractAmount(contract.getContractAmount())
                .additionalDetailInfo(createAdditionalDetailInfo(price, history))
                .client(createContractorInfo(contract.getClient()))
                .provider(createContractorInfo(contract.getProvider()))
                .clientWrittenDateTime(contract.getClientWrittenDateTime())
                .providerWrittenDateTime(contract.getProviderWrittenDateTime())
                .build();
    }

    private ContractInfo.AdditionalDetailInfo createAdditionalDetailInfo(Price price, ProjectHistory history) {
        return ContractInfo.AdditionalDetailInfo.builder()
                .workingDays(price.getWorkingDays())
                .cuts(price.getCuts())
                .additionalPanelCount(history.getAdditionalPanelCount())
                .modificationCount(price.getModificationCount())
                .additionalModificationCount(history.getAdditionalModificationCount())
                .originalFileOption(DemandType.DEMANDED.equals(history.getOriginFileDemandType()))
                .refinementOption(DemandType.DEMANDED.equals(history.getRefinementDemandType()))
                .postDurationMonth(price.getPostDurationType().getValue())
                .postDurationExtensionMonths(history.getPostDurationExtensionMonths())
                .build();
    }

    private ContractInfo.ContractorInfo createContractorInfo(Contractor contractor) {
        if (contractor == null) {
            return null;
        }

        return ContractInfo.ContractorInfo.builder()
                .companyName(contractor.getName())
                .registrationNumber(contractor.getRegistrationNumber())
                .address(contractor.getAddress())
                .representativeName(contractor.getRepresentativeName())
                .build();
    }
}
