package kr.co.yourplanet.online.business.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.entity.project.Contractor;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectContract;
import kr.co.yourplanet.core.entity.studio.Price;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.dto.response.TempContractInfo;
import kr.co.yourplanet.online.business.project.repository.ProjectContractRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectContractService {

    private final ProjectQueryService projectQueryService;
    private final ProjectContractRepository projectContractRepository;

    public TempContractInfo getTempContract(Long projectId, Long memberId) {
        Project project = projectQueryService.getById(projectId);

        // 계약 당사자인지 확인
        validateContractParty(memberId, project);

        return getByProjectId(projectId)
                .map(contract -> createTempContractInfo(project, contract))
                .orElseGet(() -> createTempContractInfo(project, null));
    }

    private TempContractInfo createTempContractInfo(Project project, ProjectContract contract) {
        // 완료된 계약인지 확인
        validateContractCompleted(contract);

        Price price = project.getCreatorPrice();

        return TempContractInfo.builder()
                .projectId(project.getId())
                .projectName("구현 예정")
                .acceptDateTime(project.getAcceptDateTime())
                .completeDateTime(project.getCompleteDateTime())
                .contractAmount((long) price.getPrice())
                .additionalDetail(createAdditionalDetail(price))
                .client(contract != null ? createContractor(contract.getClient()) : null)
                .provider(contract != null ? createContractor(contract.getProvider()) : null)
                .build();
    }

    private TempContractInfo.AdditionalDetail createAdditionalDetail(Price price) {
        return TempContractInfo.AdditionalDetail.builder()
                .workingDays(price.getWorkingDays())
                .cuts(price.getCuts())
                .modificationCount(price.getModificationCount())
                .postDurationMonthType(price.getPostDurationType())
                .build();
    }

    private TempContractInfo.ContractorInfo createContractor(Contractor contractor) {
        return TempContractInfo.ContractorInfo.builder()
                .companyName(contractor.getCompanyName())
                .registrationNumber(contractor.getRegistrationNumber())
                .address(contractor.getAddress())
                .representativeName(contractor.getRepresentativeName())
                .build();
    }

    private void validateContractParty(Long memberId, Project project) {
        if (!memberId.equals(project.getCreator().getId()) && !memberId.equals(project.getSponsor().getId())) {
            throw new BusinessException(StatusCode.FORBIDDEN, "계약서를 작성할 수 있는 멤버 타입이 아닙니다.", true);
        }
    }

    private void validateContractCompleted(ProjectContract contract) {
        if (contract != null && contract.isCompleted()) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 작성된 계약서가 존재합니다.", false);
        }
    }

    private Optional<ProjectContract> getByProjectId(Long projectId) {
        return projectContractRepository.findByProjectId(projectId);
    }
}
