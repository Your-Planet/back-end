package kr.co.yourplanet.online.business.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.repository.ProjectRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ProjectValidationService {

    private final ProjectRepository projectRepository;

    public void checkExist(long id) {
        if (!projectRepository.existsById(id)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "요청한 프로젝트를 찾을 수 없습니다.", true);
        }
    }
}
