package kr.co.yourplanet.online.business.project.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import jakarta.transaction.Transactional;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.entity.project.ProjectSubmission;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.alimtalk.util.BusinessAlimTalkSendService;
import kr.co.yourplanet.online.business.file.service.FileService;
import kr.co.yourplanet.online.business.project.dto.request.SubmissionModificateForm;
import kr.co.yourplanet.online.business.project.dto.request.SubmissionSendForm;
import kr.co.yourplanet.online.business.project.repository.ProjectSubmissionRepository;
import kr.co.yourplanet.online.business.project.service.ProjectQueryService;
import kr.co.yourplanet.online.business.project.service.ProjectValidationService;
import kr.co.yourplanet.online.business.project.service.SubmissionService;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final BusinessAlimTalkSendService alimTalkSendService;
    private final FileService fileService;

    private final ProjectSubmissionRepository projectSubmissionRepository;

    private final ProjectQueryService projectQueryService;

    private final ProjectValidationService projectValidationService;

    @Override
    @Transactional
    public void sendSubmission(Long projectId, Long creatorId, SubmissionSendForm submissionSendForm) {

        // 프로젝트-작가 관련성 체크
        projectValidationService.checkCreator(projectId, creatorId);

        Project project = projectQueryService.getById(projectId);

        // 작업물발송 가능 여부 ProjectStatus 확인
        projectValidationService.validateProjectStatusTransition(project.getCreator(), project, ProjectStatus.SUBMISSION_SENT);

        // 프로젝트 작업물 발송 내역 저장
        ProjectSubmission submission = ProjectSubmission.builder()
            .project(project)
            .seq(project.getNextSubmissionSeq())
            .sentDateTime(LocalDateTime.now())
            .build();

        projectSubmissionRepository.save(submission);

        // 작업물 파일 저장
        List<Long> submissionFileIds = submissionSendForm.submissionFileIds();
        if (!CollectionUtils.isEmpty(submissionFileIds)) {
            for (Long fileId : submissionFileIds) {
                fileService.completeUpload(fileId, creatorId, submission.getId(), FileType.PROJECT_SUBMISSION_FILE);
            }
        }

        // 프로젝트 상태 변경
        project.sendSubmission();

        // 작업물 도착 알림톡 발송
        alimTalkSendService.sendProjectSubmissionSent(projectId);

    }

    @Override
    @Transactional
    public void requestSubmissionModification(Long projectId, Long submissionId, Long sponsorId, SubmissionModificateForm submissionModificateForm) {
        // 프로젝트-광고주 관련성 체크
        projectValidationService.checkSponsor(projectId, sponsorId);

        Project project = projectQueryService.getById(projectId);

        ProjectHistory acceptedHistory = project.getAcceptedHistory()
            .orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "수락된 프로젝트가 존재하지 않습니다", false));

        // 남은 수정 요청 횟수 비교
        int totalModificationCount = project.getCreatorPrice().getModificationCount() + acceptedHistory.getAdditionalModificationCount();
        int submissionCount = project.getSubmissions().size();

        if(totalModificationCount <= submissionCount) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "요청 가능한 수정 횟수를 모두 사용하셨습니다.", false);
        }

        // 프로젝트 상태 변경
        projectValidationService.validateProjectStatusTransition(project.getSponsor(),project, ProjectStatus.REQUEST_MODIFICATION);
    }

    @Override
    public void confirmSubmission(Long projectId, Long submissionId, Long sponsorId) {
        // 프로젝트-광고주 관련성 체크
        projectValidationService.checkSponsor(projectId, sponsorId);

        Project project = projectQueryService.getById(projectId);

    }
}
