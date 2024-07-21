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
        Member sponsor = memberRepository.getById(sponsorId);
        Member author = memberRepository.getById(projectRequestForm.getAuthorId());

        if (sponsor == null || !MemberType.SPONSOR.equals(sponsor.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 광고주 정보입니다.", false);
        }

        if (author == null || !MemberType.AUTHOR.equals(author.getMemberType())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "유효하지 않은 작가 정보입니다.", false);
        }

        Project project = Project.builder()
                .author(author)
                .sponsor(sponsor)
                .projectStatus(ProjectStatus.REQUEST)
                .build();

        projectRepository.saveProject(project);

        ProjectHistory projectHistory = ProjectHistory.builder()
                .project(project)
                .seq(1)
                .title(projectRequestForm.getTitle())
                .context(projectRequestForm.getContext())
                .fromDate(projectRequestForm.getFromDate())
                .toDate(projectRequestForm.getToDate())
                .payment(projectRequestForm.getPayment())
                .cutNumber(projectRequestForm.getCutNumber())
                .categoryList(projectRequestForm.getCategoryList()) // 추후 Category Repository에서 조회하여 기입하자
                .requestMember(sponsor)
                .build();

        projectRepository.saveProjectHistory(projectHistory);
    }

    @Transactional
    public void rejectProject(ProjectRejectForm projectRejectForm, Long requestMemberId) {
        Member member = memberRepository.getById(requestMemberId);
        Project project = projectRepository.findProjectById(projectRejectForm.getId());

        checkProjectValidation(member, project);

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST,"현재 취소할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        project.changeProjectStatus(ProjectStatus.REJECT);

    }

    @Transactional
    public void negotiateProject(ProjectNegotiateForm projectNegotiateForm, Long requestMemberId) {
        Member requestMember = memberRepository.getById(requestMemberId);
        Project project = projectRepository.findProjectById(projectNegotiateForm.getId());

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
                .context(projectNegotiateForm.getContext())
                .fromDate(projectNegotiateForm.getFromDate())
                .toDate(projectNegotiateForm.getToDate())
                .payment(projectNegotiateForm.getPayment())
                .cutNumber(projectNegotiateForm.getCutNumber())
                .categoryList(projectNegotiateForm.getCategoryList()) // 추후 Category Repository에서 조회하여 기입하자
                .requestMember(requestMember)
                .build();

        projectRepository.saveProjectHistory(projectHistory);

    }

    @Transactional
    public void acceptProject(ProjectAcceptForm projectAcceptForm, Long requestMemberId) {
        Member member = memberRepository.getById(requestMemberId);
        Project project = projectRepository.findProjectById(projectAcceptForm.getId());

        checkProjectValidation(member, project);

        if(!MemberType.AUTHOR.equals(member.getMemberType())){
            throw new BusinessException(StatusCode.BAD_REQUEST, "작업수락은 작가만 할 수 있습니다", false);
        }

        if (ProjectStatus.REJECT.equals(project.getProjectStatus()) || ProjectStatus.ACCEPT.equals(project.getProjectStatus()) || ProjectStatus.COMPLETE.equals(project.getProjectStatus())) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "현재 수락할 수 없는 상태입니다 : " + project.getProjectStatus(), false);
        }

        List<ProjectHistory> projectHistoryList = projectRepository.findAllProjectHistoryListById(project);

        if(!projectHistoryList.isEmpty()){
            project.acceptProject(projectHistoryList.get(projectHistoryList.size() - 1));
        } else{
            throw new BusinessException(StatusCode.NOT_FOUND, "작업 요청 이력이 존재하지 않습니다", false);
        }

    }

    public List<ProjectHistory> getProjectHistoryList(Long id, Long requestMemberId) {
        Member member = memberRepository.getById(requestMemberId);
        Project project = projectRepository.findProjectById(id);

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
