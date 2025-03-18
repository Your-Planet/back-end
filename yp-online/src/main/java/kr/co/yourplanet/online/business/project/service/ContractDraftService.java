package kr.co.yourplanet.online.business.project.service;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.entity.member.Member;
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
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractDraftService {

    private final ProjectQueryService projectQueryService;
    private final ContractService contractService;
    private final ContractValidationService contractValidationService;
    private final MemberQueryService memberQueryService;

    /**
     * 임시 계약서 조회 및 DB에 기본 계약서 저장
     */
    public ContractInfo getContract(Long projectId, Long memberId) {
        Project project = projectQueryService.getById(projectId);
        contractValidationService.validateContractParty(memberId, project);
        ProjectContract contract = getOrCreateProjectContract(project);

        return createTempContractInfo(project, contract);
    }

    /**
     * 계약서 작성
     */
    public void draftContract(Long projectId, Long memberId, ContractDraftForm form) {
        Project project = projectQueryService.getById(projectId);

        contractValidationService.validateContractParty(memberId, project);
        contractValidationService.validateProjectStatus(project.getProjectStatus());

        ProjectContract contract = contractService.getByProjectId(projectId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "계약서가 존재하지 않습니다.", false));

        Member member = memberQueryService.getById(memberId);
        MemberType memberType = member.getMemberType();

        contractValidationService.validateContractCompleted(contract);
        contractValidationService.validateAlreadyWritten(memberType, contract);

        // 수요자/공급자 정보 입력
        Contractor contractor = createContractor(form);
        if (MemberType.CREATOR.equals(memberType)) {
            contract.writeProviderInfo(contractor);
        } else if (MemberType.SPONSOR.equals(memberType)) {
            contract.writeClientInfo(contractor);
        }

        contractService.save(contract);
    }

    private ProjectContract getOrCreateProjectContract(Project project) {
        Price price = project.getCreatorPrice();
        ProjectHistory history = project.getAcceptedHistory()
                .orElseThrow(() -> new BusinessException(StatusCode.CONFLICT, "수락된 프로젝트가 아닙니다.", false));

        return contractService.getByProjectId(project.getId())
                .orElseGet(() -> {
                    ProjectContract newContract = ProjectContract.builder()
                            .project(project)
                            .projectHistory(history)
                            .price(price)
                            .acceptDateTime(project.getAcceptDateTime())
                            .completeDateTime(project.getCompleteDateTime())
                            .contractAmount(history.getOfferPrice().longValue())
                            .build();
                    contractService.save(newContract);
                    return newContract;
                });
    }

    private ContractInfo createTempContractInfo(Project project, ProjectContract contract) {
        return ContractInfo.builder()
                .projectId(project.getId())
                .projectName(project.getOrderTitle())
                .acceptDateTime(contract.getAcceptDateTime())
                .completeDateTime(contract.getCompleteDateTime())
                .contractAmount(contract.getContractAmount())
                .additionalDetailInfo(createAdditionalDetailInfo(contract.getPrice(), contract.getProjectHistory()))
                .client(createContractorInfo(contract.getClient()))
                .provider(createContractorInfo(contract.getProvider()))
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
                .companyName(contractor.getCompanyName())
                .registrationNumber(contractor.getRegistrationNumber())
                .address(contractor.getAddress())
                .representativeName(contractor.getRepresentativeName())
                .build();
    }

    private Contractor createContractor(ContractDraftForm form) {
        return Contractor.builder()
                .companyName(form.companyName())
                .registrationNumber(form.registrationNumber())
                .address(form.address())
                .representativeName(form.representativeName())
                .build();
    }
}
