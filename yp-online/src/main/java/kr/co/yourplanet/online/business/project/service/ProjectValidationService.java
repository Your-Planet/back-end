package kr.co.yourplanet.online.business.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.enums.MemberType;
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

    public void checkAccepted(long id) {
        Project project = projectQueryService.getById(id);

        if (!ProjectStatus.ACCEPTED.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.CONFLICT, "수락된 프로젝트가 아닙니다.", false);
        }
    }

    public void checkCreator(long projectId, long memberId) {
        Project project = projectQueryService.getById(projectId);

        if (project.getCreator().getId() != memberId) {
            throw new BusinessException(StatusCode.FORBIDDEN, "해당 프로젝트의 작가가 아닙니다.", false);
        }
    }

    public void checkSponsor(long projectId, long memberId) {
        Project project = projectQueryService.getById(projectId);

        if (project.getSponsor().getId() != memberId) {
            throw new BusinessException(StatusCode.FORBIDDEN, "해당 프로젝트의 광고주가 아닙니다.", false);
        }
    }

    // 사용자 유형별 처리 가능한 ProjectStatus 검사
    public void validateProjectStatusTransition(Member requestMember, Project project, ProjectStatus targetStatus) {
        if( targetStatus == null) {
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "프로젝트 상태 변경 중 오류 발생.", false);
        }

        MemberType requestMemberType = requestMember.getMemberType();
        ProjectStatus currentStatus = project.getProjectStatus();

        // 허가되지 않은 action ProjectStatus or 액션 불가능한 프로젝트 상태
        if (!targetStatus.isTransitionAllowed(requestMemberType, currentStatus)) {
            throw new BusinessException(StatusCode.BAD_REQUEST,
                "현재 " + targetStatus.getStatusName() + " 할 수 없는 프로젝트 상태입니다", false);
        }
    }
}
