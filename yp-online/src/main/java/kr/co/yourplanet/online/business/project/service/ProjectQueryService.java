package kr.co.yourplanet.online.business.project.service;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.repository.ProjectRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectQueryService {
    private final ProjectRepository projectRepository;

    public Project getById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));
    }
}
