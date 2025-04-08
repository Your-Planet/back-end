package kr.co.yourplanet.online.business.project.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.entity.project.ProjectReferenceFile;
import kr.co.yourplanet.core.entity.studio.Price;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.dto.request.ProjectAcceptForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectNegotiateForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRejectForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRequestForm;
import kr.co.yourplanet.online.business.project.dto.response.ProjectBasicInfo;
import kr.co.yourplanet.online.business.project.dto.response.ProjectDetail;
import kr.co.yourplanet.online.business.project.dto.response.ProjectDetailInfo;
import kr.co.yourplanet.online.business.project.dto.response.ProjectHistoryForm;
import kr.co.yourplanet.online.business.project.dto.response.ProjectOverview;
import kr.co.yourplanet.online.business.project.dto.response.ProjectTimes;
import kr.co.yourplanet.online.business.project.dto.response.ReferenceFileInfo;
import kr.co.yourplanet.online.business.project.repository.ProjectHistoryRepository;
import kr.co.yourplanet.online.business.project.repository.ProjectReferenceFileRepository;
import kr.co.yourplanet.online.business.project.repository.ProjectRepository;
import kr.co.yourplanet.online.business.project.service.ProjectService;
import kr.co.yourplanet.online.business.studio.repository.PriceRepository;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import kr.co.yourplanet.online.common.util.FileUploadResult;
import kr.co.yourplanet.online.common.util.SnowflakeIdGenerator;
import kr.co.yourplanet.online.properties.FileProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    private final FileManageUtil fileManageUtil;
    private final FileProperties fileProperties;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    private final ProjectRepository projectRepository;
    private final ProjectHistoryRepository projectHistoryRepository;
    private final ProjectReferenceFileRepository projectReferenceFileRepository;
    private final MemberRepository memberRepository;
    private final PriceRepository priceRepository;

    @Override
    @Transactional
    public void createProject(ProjectRequestForm projectRequestForm, List<MultipartFile> referenceFiles,
        Long sponsorId) {
        Member sponsor = findMemberById(sponsorId);
        Price creatorPrice = priceRepository.findById(projectRequestForm.getPriceId())
            .orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "작가의 스튜디오 정보가 존재하지 않습니다", false));

        // 1. 유효성 체크
        if (!MemberType.SPONSOR.equals(sponsor.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "광고주만 프로젝트 의뢰할 수 있습니다.", false);
        }

        if (!MemberType.CREATOR.equals(creatorPrice.getProfile().getMember().getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "작가에게만 의뢰할 수 있습니다.", false);
        }

        if (CollectionUtils.isEmpty(projectRequestForm.getPostStartDates())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "광고 날짜를 최소 1개 이상 선택해 주세요.", false);
        } else if (projectRequestForm.getPostStartDates().size() > 10) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "광고 날짜는 최대 10개까지 선택 가능합니다.", false);
        }

        // 주문 코드 생성
        String orderCode = snowflakeIdGenerator.generateId("P");

        // 프로젝트 저장
        Project project = Project.builder()
            .creator(creatorPrice.getProfile().getMember())
            .sponsor(sponsor)
            .projectStatus(ProjectStatus.IN_REVIEW)
            .brandName(projectRequestForm.getBrandName())
            .orderTitle(projectRequestForm.getOrderTitle())
            .orderCode(orderCode)
            .referenceUrls(projectRequestForm.getReferenceUrls()) // 캠페인 URL
            .campaignDescription(projectRequestForm.getCampaignDescription()) // 캠페인 소개
            .requestDateTime(LocalDateTime.now())
            .creatorPrice(creatorPrice)
            .build();

        projectRepository.save(project);

        // 프로젝트 히스토리 저장
        ProjectHistory projectHistory = ProjectHistory.builder()
            .project(project) // 필요한 Project 객체
            .seq(1) // 시퀀스 번호
            .projectStatus(ProjectStatus.IN_REVIEW)
            .additionalPanelCount(projectRequestForm.getAdditionalModification().getCount()) // 추가 컷 수
            .additionalPanelNegotiable(projectRequestForm.getAdditionalPanel().getIsNegotiable())
            .additionalModificationCount(projectRequestForm.getAdditionalModification().getCount()) // 추가 수정 횟수
            .originFileDemandType(projectRequestForm.getOriginFile().getDemandType()) // 원본 파일 요청 여부
            .refinementDemandType(projectRequestForm.getRefinement().getDemandType()) // 2차 활용 요청 여부
            .postDurationExtensionMonths(projectRequestForm.getPostDurationExtension().getMonths()) // 업로드 기간 연장 (월)
            .postStartDates(projectRequestForm.getPostStartDates()) // 광고 시작 날짜 리스트
            .dueDate(projectRequestForm.getDueDate()) // 작업 기한
            .offerPrice(projectRequestForm.getOfferPrice()) // 제안 금액
            .message(projectRequestForm.getMessage()) // 기타 요청사항
            .requestMember(sponsor) // 요청한 회원 (실제 Member 객체 필요)
            .build();
        projectHistoryRepository.save(projectHistory);

        // 참고자료 저장
        // TODO: 파일 메타데이터 참고 키 수정
        if (!CollectionUtils.isEmpty(referenceFiles)) {
            for (MultipartFile referenceFile : referenceFiles) {
                FileUploadResult uploadResult = fileManageUtil.uploadFile(referenceFile,
                    FileType.PROJECT_REFERENCE_FILE);
                ProjectReferenceFile projectReferenceFile = ProjectReferenceFile.builder()
                    .project(project)
                    .originalFileName(uploadResult.getOriginalFileName())
                    .randomFileName(uploadResult.getRandomFileName())
                    .referenceFilePath(uploadResult.getFilePath())
                    .referenceFileUrl(uploadResult.getFileUrl())
                    .build();
                projectReferenceFileRepository.save(projectReferenceFile);
            }
        }

    }

    @Override
    @Transactional
    public void rejectProject(ProjectRejectForm projectRejectForm, Long requestMemberId) {
        Member member = findMemberById(requestMemberId);
        Project project = findProjectById(projectRejectForm.getId());
        ProjectStatus projectStatusAction = null;

        // Validation
        checkProjectValidation(member, project);

        // 취소or거절 가능 상태인지 확인
        if (MemberType.CREATOR.equals(member.getMemberType())) {
            projectStatusAction = ProjectStatus.REJECTED;

        } else if (MemberType.SPONSOR.equals(member.getMemberType())) {
            projectStatusAction = ProjectStatus.CANCELED;
        }
        validateProjectStatusTransition(member, project, projectStatusAction);

        project.invalidate(projectStatusAction, projectRejectForm.getReason());

    }

    @Override
    @Transactional
    public void negotiateProject(ProjectNegotiateForm projectNegotiateForm, Long requestMemberId) {
        Member requestMember = findMemberById(requestMemberId);
        Project project = findProjectById(projectNegotiateForm.getProjectId());
        ProjectStatus projectStatusAction = null;

        // Validation
        checkProjectValidation(requestMember, project);

        if (MemberType.CREATOR.equals(requestMember.getMemberType())) {
            projectStatusAction = ProjectStatus.NEGOTIATION_FROM_CREATOR;
        } else if (MemberType.SPONSOR.equals(requestMember.getMemberType())) {
            projectStatusAction = ProjectStatus.NEGOTIATION_FROM_SPONSOR;
        } else {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 유형입니다.", false);
        }
        validateProjectStatusTransition(requestMember, project, projectStatusAction);

        // 프로젝트 업데이트
        project.negotiate(projectStatusAction);
        project.getReferenceUrls().addAll(projectNegotiateForm.getReferenceUrls());

        // 프로젝트 히스토리 적재
        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAllByProject(project);
        Integer seq = projectHistoryList.get(projectHistoryList.size() - 1).getSeq() + 1;

        ProjectHistory projectHistory = ProjectHistory.builder()
            .project(project)
            .seq(seq)
            .projectStatus(projectStatusAction)
            .additionalPanelCount(projectNegotiateForm.getAdditionalModification().getCount()) // 추가 컷 수
            .additionalModificationCount(projectNegotiateForm.getAdditionalModification().getCount()) // 추가 수정 횟수
            .postDurationExtensionMonths(projectNegotiateForm.getPostDurationExtension().getMonths()) // 업로드 기간 연장 (월)
            .originFileDemandType(projectNegotiateForm.getOriginFile().getDemandType()) // 원본 파일 요청 여부
            .refinementDemandType(projectNegotiateForm.getRefinement().getDemandType()) // 2차 활용 요청 여부
            .postStartDates(projectNegotiateForm.getPostStartDates()) // 광고 시작 날짜 리스트
            .dueDate(projectNegotiateForm.getDueDate()) // 작업 기한
            .offerPrice(projectNegotiateForm.getOfferPrice()) // 제안 금액
            .message(projectNegotiateForm.getMessage()) // 기타 요청사항
            .requestMember(requestMember) // 요청한 회원 (실제 Member 객체 필요)
            .build();
        projectHistoryRepository.save(projectHistory);

    }

    @Override
    @Transactional
    public void acceptProject(ProjectAcceptForm projectAcceptForm, Long requestMemberId) {
        Member member = memberRepository.findById(requestMemberId)
            .orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectAcceptForm.getId())
            .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));

        // Validation
        checkProjectValidation(member, project);
        validateProjectStatusTransition(member, project, ProjectStatus.IN_PROGRESS);

        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAllByProject(project);

        if (!projectHistoryList.isEmpty()) {
            project.accept(projectHistoryList.get(projectHistoryList.size() - 1));
        } else {
            throw new BusinessException(StatusCode.NOT_FOUND, "작업 요청 이력이 존재하지 않습니다", false);
        }

    }

    @Override
    public List<ProjectHistoryForm> getProjectHistoryList(Long id, Long requestMemberId) {
        // return List
        List<ProjectHistoryForm> projectHistoryFormList = new ArrayList<>();

        Member member = memberRepository.findById(requestMemberId)
            .orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(id)
            .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));

        checkProjectValidation(member, project);

        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAllByProject(project);

        if (CollectionUtils.isEmpty(projectHistoryList)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false);
        }

        for (ProjectHistory projectHistory : projectHistoryList) {
            ProjectHistoryForm projectHistoryForm = new ProjectHistoryForm(projectHistory);
            projectHistoryFormList.add(projectHistoryForm);
        }

        return projectHistoryFormList;
    }

    @Override
    public List<ProjectBasicInfo> getMemberProjectsBasicInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        List<Project> projectList = null;
        List<ProjectBasicInfo> projectBasicInfoList = new ArrayList<>();

        if (MemberType.SPONSOR.equals(member.getMemberType())) {
            projectList = projectRepository.findAllBySponsor(member);
        } else if (MemberType.CREATOR.equals(member.getMemberType())) {
            projectList = projectRepository.findAllByCreator(member);
        }

        if (!CollectionUtils.isEmpty(projectList)) {
            projectBasicInfoList = projectList.stream()
                .map(project -> ProjectBasicInfo.builder()
                    .id(project.getId())
                    .sponsorName(project.getSponsor().getName())
                    .creatorName(project.getCreator().getName())
                    .brandName(project.getBrandName())
                    .orderTitle(project.getOrderTitle())
                    .orderCode(project.getOrderCode())
                    .campaignDescription(project.getCampaignDescription())
                    .projectStatus(project.getProjectStatus())
                    .projectTimes(new ProjectTimes(project))
                    .build())
                .toList();
        }

        return projectBasicInfoList;
    }

    @Override
    public ProjectDetailInfo getProjectDetailInfo(Long projectId, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));

        checkProjectValidation(member, project);

        ProjectHistory latestProjectHistory = project.getLatestHistory()
            .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "의뢰 내역이 존재하지 않습니다.", false));
        Price creatorPrice = project.getCreatorPrice();
        List<ReferenceFileInfo> referenceFileInfoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(project.getReferenceFiles())) {
            referenceFileInfoList = project.getReferenceFiles().stream()
                .map(projectReferenceFile -> ReferenceFileInfo.builder()
                    .originalFileName(projectReferenceFile.getOriginalFileName())
                    .fileUrl(fileProperties.getBaseUrl() + projectReferenceFile.getReferenceFileUrl())
                    .build()
                ).collect(Collectors.toList());
        }

        // DTO 생성
        ProjectOverview overview = ProjectOverview.builder()
            .sponsorName(project.getSponsor().getName())
            .creatorName(project.getCreator().getName())
            .brandName(project.getBrandName())
            .orderTitle(project.getOrderTitle())
            .orderCode(project.getOrderCode())
            .dueDate(latestProjectHistory.getDueDate())
            .defaultPanelCount(creatorPrice.getCuts())
            .additionalPanelCount(latestProjectHistory.getAdditionalPanelCount())
            .defaultModificationCount(creatorPrice.getModificationCount())
            .additionalModificationCount(latestProjectHistory.getAdditionalModificationCount())
            .offerPrice(latestProjectHistory.getOfferPrice())
            .build();

        ProjectDetail detail = ProjectDetail.builder()
            .campaignDescription(project.getCampaignDescription())
            .referenceUrls(project.getReferenceUrls())
            .referenceFiles(referenceFileInfoList)
            .latestProjectHistory(new ProjectHistoryForm(latestProjectHistory))
            .build();

        return ProjectDetailInfo.builder()
            .overview(overview)
            .detail(detail)
            .projectHistories(project.getProjectHistories().stream()
                .map(ProjectHistoryForm::new)
                .collect(Collectors.toList()))
            .projectStatus(project.getProjectStatus())
            .projectTimes(new ProjectTimes(project))
            .build();

    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
    }

    private Project findProjectById(Long projectId) {
        return projectRepository.findById(projectId)
            .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "프로젝트를 찾을 수 없습니다.", false));
    }

    private void checkProjectValidation(Member member, Project project) {
        // Validation Check

        // 요청자 존재여부
        if (member == null) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false);
        }

        // Project 존재여부
        if (project == null) {
            throw new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다", false);
        }

        // 요청자가 Project와 관련되어 있는지 체크 (요청자 = 작가 or 광고주)
        if (MemberType.CREATOR.equals(member.getMemberType()) && !member.equals(project.getCreator())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "사용자의 작업의뢰가 아닙니다", false);
        } else if (MemberType.SPONSOR.equals(member.getMemberType()) && !member.equals(project.getSponsor())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "사용자가 의뢰한 작업이 아닙니다", false);
        }
    }

    // 사용자 유형별 처리 가능한 ProjectStatus 검사
    private void validateProjectStatusTransition(Member requestMember, Project project, ProjectStatus targetStatus) {
        MemberType requestMemberType = requestMember.getMemberType();
        ProjectStatus currentStatus = project.getProjectStatus();

        // 허가되지 않은 action ProjectStatus or 액션 불가능한 프로젝트 상태
        if (!targetStatus.isTransitionAllowed(requestMemberType, currentStatus)) {
            throw new BusinessException(StatusCode.BAD_REQUEST,
                "현재 " + targetStatus.getStatusName() + " 할 수 없는 프로젝트 상태입니다", false);
        }

    }
}
