package kr.co.yourplanet.online.business.project.controller;

import kr.co.yourplanet.core.entity.project.ProjectHistory;
import kr.co.yourplanet.online.business.project.dto.request.ProjectAcceptForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectNegotiateForm;
import kr.co.yourplanet.online.business.project.dto.response.ProjectHistories;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRejectForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRequestForm;
import kr.co.yourplanet.online.business.project.service.ProjectService;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.core.enums.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/project/request")
    public ResponseEntity<ResponseForm<String>> requestNewProject(@Valid @RequestBody ProjectRequestForm projectRequestForm, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.requestProject(projectRequestForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/project/reject")
    public ResponseEntity<ResponseForm<String>> rejectProject(@Valid @RequestBody ProjectRejectForm projectRejectForm, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.rejectProject(projectRejectForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/project/negotiate")
    public ResponseEntity<ResponseForm<String>> negotiateProject(@Valid @RequestBody ProjectNegotiateForm projectNegotiateForm, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.negotiateProject(projectNegotiateForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/project/accept")
    public ResponseEntity<ResponseForm<String>> acceptProject(@Valid @RequestBody ProjectAcceptForm projectAcceptForm, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.acceptProject(projectAcceptForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @GetMapping("/project/history")
    public ResponseEntity<ResponseForm<List<ProjectHistories>>> getProjectHistoryList(@RequestParam("id") Long projectId, @AuthenticationPrincipal JwtPrincipal principal) {
        List<ProjectHistories> projectHistoriesList = new ArrayList<>();

        List<ProjectHistory> projectHistoryList = projectService.getProjectHistoryList(projectId, principal.getId());

        for (ProjectHistory projectHistory : projectHistoryList) {
            ProjectHistories projectHistories = ProjectHistories.builder()
                    .id(projectHistory.getProject().getId())
                    .seq(projectHistory.getSeq())
                    .requestContext(projectHistory.getContext())
                    .fromDate(projectHistory.getFromDate())
                    .toDate(projectHistory.getToDate())
                    .payment(projectHistory.getPayment())
                    .cutNumber(projectHistory.getCutNumber())
                    .categoryList(projectHistory.getCategoryList())
                    .requestMemberType(projectHistory.getRequestMember().getMemberType())
                    .build();

            projectHistoriesList.add(projectHistories);
        }

        ResponseForm<List<ProjectHistories>> responseForm = new ResponseForm<>(StatusCode.OK, projectHistoriesList);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }
}
