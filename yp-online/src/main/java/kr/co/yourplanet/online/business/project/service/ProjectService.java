package kr.co.yourplanet.online.business.project.service;

import kr.co.yourplanet.online.business.project.dto.request.*;
import kr.co.yourplanet.online.business.project.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    void requestProject(ProjectRequestForm projectRequestForm, List<MultipartFile> referenceFiles, Long sponsorId);

    void rejectProject(ProjectRejectForm projectRejectForm, Long requestMemberId);

    void negotiateProject(ProjectNegotiateForm projectNegotiateForm, Long requestMemberId);

    void acceptProject(ProjectAcceptForm projectAcceptForm, Long requestMemberId);

    List<ProjectHistoryForm> getProjectHistoryList(Long id, Long requestMemberId);

    List<ProjectBasicInfo> getMemberProjectsBasicInfo(Long memberId);

    ProjectDetailInfo getProjectDetailInfo(Long projectId, Long memberId);
}