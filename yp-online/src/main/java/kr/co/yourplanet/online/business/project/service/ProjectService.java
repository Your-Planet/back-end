package kr.co.yourplanet.online.business.project.service;

import java.util.List;

import kr.co.yourplanet.online.business.project.dto.request.ProjectAcceptForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectNegotiateForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRejectForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRequestForm;
import kr.co.yourplanet.online.business.project.dto.response.ProjectBasicInfo;
import kr.co.yourplanet.online.business.project.dto.response.ProjectDetailInfo;
import kr.co.yourplanet.online.business.project.dto.response.ProjectHistoryForm;

public interface ProjectService {

    void createProject(ProjectRequestForm projectRequestForm, Long sponsorId);

    void rejectProject(ProjectRejectForm projectRejectForm, Long requestMemberId);

    void negotiateProject(ProjectNegotiateForm projectNegotiateForm, Long requestMemberId);

    void acceptProject(ProjectAcceptForm projectAcceptForm, Long requestMemberId);

    List<ProjectHistoryForm> getProjectHistoryList(Long id, Long requestMemberId);

    List<ProjectBasicInfo> getMemberProjectsBasicInfo(Long memberId);

    ProjectDetailInfo getProjectDetailInfo(Long projectId, Long memberId);
}