package kr.co.yourplanet.online.business.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.repository.ProjectRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProjectValidationService {

    private final ProjectQueryService projectQueryService;
    private final ProjectRepository projectRepository;

    public void checkExist(long id) {
        if (!projectRepository.existsById(id)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "요청한 프로젝트를 찾을 수 없습니다.", true);
        }
    }

    public void checkInProgress(long id) {
        Project project = projectQueryService.getById(id);

        if (!ProjectStatus.IN_PROGRESS.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.CONFLICT, "수락된 프로젝트가 아닙니다.", false);
        }
    }

    public void checkSponsor(long id, long memberId) {
        Project project = projectQueryService.getById(id);

        if (project.getSponsor().getId() != memberId) {
            throw new BusinessException(StatusCode.FORBIDDEN, "해당 프로젝트의 광고주가 아닙니다.", false);
        }
    }
}
