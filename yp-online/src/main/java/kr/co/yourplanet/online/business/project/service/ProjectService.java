package kr.co.yourplanet.online.business.project.service;

import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.core.enums.ProjectStatus;
import kr.co.yourplanet.online.business.project.dto.request.ProjectAcceptForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectNegotiateForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRejectForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRequestForm;
import kr.co.yourplanet.online.business.project.repository.ProjectRepository;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void requestProject(ProjectRequestForm projectRequestForm, Long sponsorId) {
        Member sponsor = memberRepository.findById(sponsorId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 광고주 정보입니다.", false));
        Member author = memberRepository.findById(projectRequestForm.getAuthorId()).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 작가 정보입니다.", false));

        if (!MemberType.SPONSOR.equals(sponsor.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 광고주 정보입니다.", false);
        }

        if (!MemberType.AUTHOR.equals(author.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 작가 정보입니다.", false);
        }

        Project project = Project.builder()
                .author(author)
                .sponsor(sponsor)
                .projectStatus(ProjectStatus.REQUEST)
                .build();

        projectRepository.saveProject(project);

        ProjectHistory projectHistory = ProjectHistory.builder()
                .project(project) // 필요한 Project 객체
                .seq(1) // 시퀀스 번호
                .additionalCuts(projectRequestForm.getAdditionalCuts()) // 추가 컷 수
                .modificationCount(projectRequestForm.getModificationCount()) // 추가 수정 횟수
                .additionalPostDurationMonth(projectRequestForm.getAdditionalPostDurationMonth()) // 업로드 기간 연장 (월)
                .isOriginFileRequest(projectRequestForm.isOriginFileRequest()) // 원본 파일 요청 여부
                .isRefinementRequest(projectRequestForm.isRefinementRequest()) // 2차 활용 요청 여부
                .postDates(projectRequestForm.getPostDates()) // 광고 기간 날짜 지정
                .postFromDate(projectRequestForm.getPostFromDate()) // 광고 시작 날짜
                .postToDate(projectRequestForm.getPostToDate()) // 광고 종료 날짜
                .dueDate(projectRequestForm.getDueDate()) // 작업 기한
                .brandName(projectRequestForm.getBrandName()) // 브랜드명
                .brandUrls(projectRequestForm.getBrandUrls()) // 브랜드 URL
                .campaignDescription(projectRequestForm.getCampaignDescription()) // 캠페인 소개
                .campaignUrls(projectRequestForm.getCampaignUrls()) // 캠페인 URL
                .offerPrice(projectRequestForm.getOfferPrice()) // 제안 금액
                .requestNotes(projectRequestForm.getRequestNotes()) // 기타 요청사항
                .requestMember(sponsor) // 요청한 회원 (실제 Member 객체 필요)
                .categoryList(projectRequestForm.getCategoryList()) // 카테고리 리스트
                .build();

        projectRepository.saveProjectHistory(projectHistory);
    }

    @Transactional
    public void rejectProject(ProjectRejectForm projectRejectForm, Long requestMemberId) {
        Member member = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectRejectForm.getId());

        checkProjectValidation(member, project);

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 취소할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        project.changeProjectStatus(ProjectStatus.REJECT);

    }

    @Transactional
    public void negotiateProject(ProjectNegotiateForm projectNegotiateForm, Long requestMemberId) {
        Member requestMember = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectNegotiateForm.getId());

        checkProjectValidation(requestMember, project);

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 취소할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        project.changeProjectStatus(ProjectStatus.NEGOTIATION);

        List<ProjectHistory> projectHistoryList = projectRepository.findAllProjectHistoryListById(project);
        Integer seq = projectHistoryList.get(projectHistoryList.size() - 1).getSeq() + 1;

        ProjectHistory projectHistory = ProjectHistory.builder()
                .project(project)
                .seq(seq)
                .additionalCuts(projectNegotiateForm.getAdditionalCuts()) // 추가 컷 수
                .modificationCount(projectNegotiateForm.getModificationCount()) // 추가 수정 횟수
                .additionalPostDurationMonth(projectNegotiateForm.getAdditionalPostDurationMonth()) // 업로드 기간 연장 (월)
                .isOriginFileRequest(projectNegotiateForm.isOriginFileRequest()) // 원본 파일 요청 여부
                .isRefinementRequest(projectNegotiateForm.isRefinementRequest()) // 2차 활용 요청 여부
                .postDates(projectNegotiateForm.getPostDates()) // 광고 기간 날짜 지정
                .postFromDate(projectNegotiateForm.getPostFromDate()) // 광고 시작 날짜
                .postToDate(projectNegotiateForm.getPostToDate()) // 광고 종료 날짜
                .dueDate(projectNegotiateForm.getDueDate()) // 작업 기한
                .brandName(projectNegotiateForm.getBrandName()) // 브랜드명
                .brandUrls(projectNegotiateForm.getBrandUrls()) // 브랜드 URL
                .campaignDescription(projectNegotiateForm.getCampaignDescription()) // 캠페인 소개
                .campaignUrls(projectNegotiateForm.getCampaignUrls()) // 캠페인 URL
                .offerPrice(projectNegotiateForm.getOfferPrice()) // 제안 금액
                .requestNotes(projectNegotiateForm.getRequestNotes()) // 기타 요청사항
                .requestMember(requestMember) // 요청한 회원 (실제 Member 객체 필요)
                .categoryList(projectNegotiateForm.getCategoryList()) // 카테고리 리스트
                .build();

        projectRepository.saveProjectHistory(projectHistory);

    }

    @Transactional
    public void acceptProject(ProjectAcceptForm projectAcceptForm, Long requestMemberId) {
        Member member = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(projectAcceptForm.getId());

        checkProjectValidation(member, project);

        if (!MemberType.AUTHOR.equals(member.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "작업수락은 작가만 할 수 있습니다", false);
        }

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 수락할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        List<ProjectHistory> projectHistoryList = projectRepository.findAllProjectHistoryListById(project);

        if (!projectHistoryList.isEmpty()) {
            project.acceptProject(projectHistoryList.get(projectHistoryList.size() - 1));
        } else {
            throw new BusinessException(StatusCode.NOT_FOUND, "작업 요청 이력이 존재하지 않습니다", false);
        }

    }

    public List<ProjectHistory> getProjectHistoryList(Long id, Long requestMemberId) {
        Member member = memberRepository.findById(requestMemberId).orElseThrow(() -> new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false));
        Project project = projectRepository.findById(id);

        checkProjectValidation(member, project);

        return projectRepository.findAllProjectHistoryListById(project);
    }

    private void checkProjectValidation(Member member, Project project) {
        // Validation Check

        // 요청자 존재여부
        if (member == null) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 사용자 요청입니다.", false);
        }

        // Project 존재여부
        if (project == null) {
            throw new BusinessException(StatusCode.NOT_FOUND, "존재하지 않는 작업내역입니다", false);
        }

        // 요청자가 Project와 관련되어 있는지 체크 (요청자 = 작가 or 광고주)
        if (MemberType.AUTHOR.equals(member.getMemberType())) {
            if (!member.equals(project.getAuthor())) {
                throw new BusinessException(StatusCode.BAD_REQUEST, "사용자의 작업내역이 아닙니다", false);
            }
        } else if (MemberType.SPONSOR.equals(member.getMemberType()) && !member.equals(project.getSponsor())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "사용자의 작업내역이 아닙니다", false);
        }
    }
}
