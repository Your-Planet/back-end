package kr.co.yourplanet.online.business.settlement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.settlement.ProjectSettlement;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.settlement.repository.ProjectSettlementRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProjectSettlementQueryService {

    private final ProjectSettlementRepository projectSettlementRepository;

    public ProjectSettlement getByProjectId(long projectId) {
        return projectSettlementRepository.findByProjectId(projectId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "요청한 정산 정보를 찾을 수 없습니다.", true));
    }
}
