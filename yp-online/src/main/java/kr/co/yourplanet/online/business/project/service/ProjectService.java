package kr.co.yourplanet.online.business.project.service;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.entity.project.ProjectReferenceFile;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.dto.request.*;
import kr.co.yourplanet.online.business.project.dto.response.ProjectBasicInfo;
import kr.co.yourplanet.online.business.project.dto.response.ProjectHistoryForm;
import kr.co.yourplanet.online.business.project.repository.ProjectHistoryRepository;
import kr.co.yourplanet.online.business.project.repository.ProjectReferenceFileRepository;
import kr.co.yourplanet.online.business.project.repository.ProjectRepository;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.common.util.FileManageUtil;
import kr.co.yourplanet.online.common.util.FileUploadResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final FileManageUtil fileManageUtil;

    private final ProjectRepository projectRepository;
    private final ProjectHistoryRepository projectHistoryRepository;
    private final ProjectReferenceFileRepository projectReferenceFileRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void requestProject(ProjectRequestForm projectRequestForm, List<MultipartFile> referenceFiles, Long sponsorId) {
        Member sponsor = memberRepository.findById(sponsorId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 광고주 정보입니다.", false));
        Member creator = memberRepository.findById(projectRequestForm.getCreatorId()).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 작가 정보입니다.", false));

        // 1. 유효성 체크
        if (!MemberType.SPONSOR.equals(sponsor.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "광고주만 프로젝트 의뢰할 수 있습니다.", false);
        }

        if (!MemberType.CREATOR.equals(creator.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "작가에게만 의뢰할 수 있습니다.", false);
        }

        if (CollectionUtils.isEmpty(projectRequestForm.getPostStartDates())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "광고 날짜를 최소 1개 이상 선택해 주세요.", false);
        } else if (projectRequestForm.getPostStartDates().size() > 10) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "광고 날짜는 최대 10개까지 선택 가능합니다.", false);
        }

        Project project = Project.builder()
                .creator(creator)
                .sponsor(sponsor)
                .projectStatus(ProjectStatus.REQUEST)
                .build();

        projectRepository.save(project);

        ProjectHistory projectHistory = ProjectHistory.builder()
                .project(project) // 필요한 Project 객체
                .seq(1) // 시퀀스 번호
                .additionalPanelCount(projectRequestForm.getAdditionalModification().getCount()) // 추가 컷 수
                .additionalModificationCount(projectRequestForm.getAdditionalModification().getCount()) // 추가 수정 횟수
                .postDurationExtensionMonths(projectRequestForm.getPostDurationExtension().getMonths()) // 업로드 기간 연장 (월)
                .originFileDemandType(projectRequestForm.getOriginFile().getDemandType()) // 원본 파일 요청 여부
                .refinementDemandType(projectRequestForm.getRefinement().getDemandType()) // 2차 활용 요청 여부
                .postStartDates(projectRequestForm.getPostStartDates()) // 광고 시작 날짜 리스트
                .dueDate(projectRequestForm.getDueDate()) // 작업 기한
                .brandName(projectRequestForm.getBrandName()) // 브랜드명
                .campaignDescription(projectRequestForm.getCampaignDescription()) // 캠페인 소개
                .referenceUrls(projectRequestForm.getReferenceUrls()) // 캠페인 URL
                .offerPrice(projectRequestForm.getOfferPrice()) // 제안 금액
                .message(projectRequestForm.getMessage()) // 기타 요청사항
                .requestMember(sponsor) // 요청한 회원 (실제 Member 객체 필요)
                //.categoryList(projectRequestForm.getCategoryList()) // 카테고리 리스트
                .build();

        projectHistoryRepository.save(projectHistory);

        // 참고자료 저장
        if (!CollectionUtils.isEmpty(referenceFiles)) {
            int seq = 1;
            for (MultipartFile referenceFile : referenceFiles) {
                FileUploadResult uploadResult = fileManageUtil.uploadFile(referenceFile, FileType.PROJECT_REFERENCE_FILE);
                ProjectReferenceFile projectReferenceFile = ProjectReferenceFile.builder()
                        .project(project)
                        .seq(seq)
                        .originalFileName(uploadResult.getOriginalFileName())
                        .randomFileName(uploadResult.getRandomFileName())
                        .referenceFilePath(uploadResult.getFilePath())
                        .referenceFileUrl(uploadResult.getFileUrl())
                        .build();
                projectReferenceFileRepository.save(projectReferenceFile);
                seq++;
            }
        }

    }

    @Transactional
    public void rejectProject(ProjectRejectForm projectRejectForm, Long requestMemberId) {
        Member member = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectRejectForm.getId()).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));

        checkProjectValidation(member, project);

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 취소할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        project.changeProjectStatus(ProjectStatus.REJECT);

    }

    @Transactional
    public void negotiateProject(ProjectNegotiateForm projectNegotiateForm, Long requestMemberId) {
        Member requestMember = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectNegotiateForm.getProjectId()).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));

        checkProjectValidation(requestMember, project);

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 취소할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        project.changeProjectStatus(ProjectStatus.NEGOTIATION);

        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAllByProject(project);
        Integer seq = projectHistoryList.get(projectHistoryList.size() - 1).getSeq() + 1;

        ProjectHistory projectHistory = ProjectHistory.builder()
                .project(project)
                .seq(seq)
                .additionalPanelCount(projectNegotiateForm.getAdditionalModification().getCount()) // 추가 컷 수
                .additionalModificationCount(projectNegotiateForm.getAdditionalModification().getCount()) // 추가 수정 횟수
                .postDurationExtensionMonths(projectNegotiateForm.getPostDurationExtension().getMonths()) // 업로드 기간 연장 (월)
                .originFileDemandType(projectNegotiateForm.getOriginFile().getDemandType()) // 원본 파일 요청 여부
                .refinementDemandType(projectNegotiateForm.getRefinement().getDemandType()) // 2차 활용 요청 여부
                .postStartDates(projectNegotiateForm.getPostStartDates()) // 광고 시작 날짜 리스트
                .dueDate(projectNegotiateForm.getDueDate()) // 작업 기한
                .brandName(projectNegotiateForm.getBrandName()) // 브랜드명
                .campaignDescription(projectNegotiateForm.getCampaignDescription()) // 캠페인 소개
                .referenceUrls(projectNegotiateForm.getReferenceUrls()) // 캠페인 URL
                .offerPrice(projectNegotiateForm.getOfferPrice()) // 제안 금액
                .message(projectNegotiateForm.getMessage()) // 기타 요청사항
                .requestMember(requestMember) // 요청한 회원 (실제 Member 객체 필요)
                .build();

        projectHistoryRepository.save(projectHistory);

    }

    @Transactional
    public void acceptProject(ProjectAcceptForm projectAcceptForm, Long requestMemberId) {
        Member member = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectAcceptForm.getId()).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));

        checkProjectValidation(member, project);

        if (!MemberType.CREATOR.equals(member.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "작업수락은 작가만 할 수 있습니다", false);
        }

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 수락할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAllByProject(project);

        if (!projectHistoryList.isEmpty()) {
            project.acceptProject(projectHistoryList.get(projectHistoryList.size() - 1));
        } else {
            throw new BusinessException(StatusCode.NOT_FOUND, "작업 요청 이력이 존재하지 않습니다", false);
        }

    }

    public List<ProjectHistoryForm> getProjectHistoryList(Long id, Long requestMemberId) {
        // return List
        List<ProjectHistoryForm> projectHistoryFormList = new ArrayList<>();

        Member member = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(id).orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 의뢰입니다.", false));

        checkProjectValidation(member, project);

        List<ProjectHistory> projectHistoryList = projectHistoryRepository.findAllByProject(project);

        if (CollectionUtils.isEmpty(projectHistoryList)) {
            throw new BusinessException(StatusCode.NOT_FOUND, "스튜디오 정보가 존재하지 않습니다.", false);
        }

        for (ProjectHistory projectHistory : projectHistoryList) {
            ProjectHistoryForm projectHistoryForm = ProjectHistoryForm.builder()
                    .id(projectHistory.getProject().getId())  // Project ID를 가져옵니다.
                    .seq(projectHistory.getSeq())
                    .additionalPanel(ProjectCommonAttribute.ProjectAdditionalPanel.builder()
                            .count(projectHistory.getAdditionalPanelCount())
                            .isNegotiable(projectHistory.getAdditionalPanelNegotiable())
                            .build())
                    .additionalModification(ProjectCommonAttribute.ProjectAdditionalModification.builder()
                            .count(projectHistory.getAdditionalModificationCount())
                            .build())
                    .originFile(ProjectCommonAttribute.ProjectOriginFile.builder()
                            .demandType(projectHistory.getOriginFileDemandType())
                            .build())
                    .refinement(ProjectCommonAttribute.ProjectRefinement.builder()
                            .demandType(projectHistory.getRefinementDemandType())
                            .build())
                    .postDurationExtension(ProjectCommonAttribute.ProjectPostDurationExtension.builder()
                            .months(projectHistory.getPostDurationExtensionMonths())
                            .build())
                    .postStartDates(projectHistory.getPostStartDates())
                    .dueDate(projectHistory.getDueDate())
                    .brandName(projectHistory.getBrandName())
                    .campaignDescription(projectHistory.getCampaignDescription())
                    .referenceUrls(projectHistory.getReferenceUrls())
                    .offerPrice(projectHistory.getOfferPrice())
                    .message(projectHistory.getMessage())
                    .requestMemberType(projectHistory.getRequestMember().getMemberType())  // 요청 멤버의 타입을 가져옵니다.
                    .build();

            projectHistoryFormList.add(projectHistoryForm);
        }

        return projectHistoryFormList;
    }

    public List<ProjectBasicInfo> getMemberProjectsBasicInfo(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
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
                            .campaignDescription(project.getCampaignDescription())
                            .projectStatus(project.getProjectStatus())
                            .build())
                    .collect(Collectors.toList());
        }

        return projectBasicInfoList;
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
        if (MemberType.CREATOR.equals(member.getMemberType())) {
            if (!member.equals(project.getCreator())) {
                throw new BusinessException(StatusCode.BAD_REQUEST, "사용자의 작업의뢰가 아닙니다", false);
            }
        } else if (MemberType.SPONSOR.equals(member.getMemberType()) && !member.equals(project.getSponsor())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "사용자의 작업의뢰가 아닙니다", false);
        }
    }
}
