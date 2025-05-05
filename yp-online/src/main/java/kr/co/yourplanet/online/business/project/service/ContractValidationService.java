package kr.co.yourplanet.online.business.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectContract;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;

@Transactional(readOnly = true)
@Service
public class ContractValidationService {

    public void validateAlreadyWritten(MemberType memberType, ProjectContract contract) {
        if (MemberType.CREATOR.equals(memberType) && contract.isProviderWritten()) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 작성한 계약서 입니다.", false);
        }
        if (MemberType.SPONSOR.equals(memberType) && contract.isClientWritten()) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 작성한 계약서 입니다.", false);
        }
    }

    public void validateProjectStatus(ProjectStatus status) {
        if (!ProjectStatus.ACCEPTED.equals(status)) {
            throw new BusinessException(StatusCode.CONFLICT, "승인되지 않은 프로젝트에서는 계약서를 작성할 수 없습니다.", false);
        }
    }

    public void validateContractParty(Long memberId, Project project) {
        long creatorId = project.getCreator().getId();
        long sponsorId = project.getSponsor().getId();

        if (!memberId.equals(creatorId) && !memberId.equals(sponsorId)) {
            throw new BusinessException(StatusCode.FORBIDDEN, "계약 당사자가 아니므로 작업을 수행할 수 없습니다.", true);
        }
    }

    public void validateContractCompleted(ProjectContract contract) {
        if (contract != null && contract.isCompleted()) {
            throw new BusinessException(StatusCode.CONFLICT, "이미 완료된 계약입니다.", false);
        }
    }
}
