package kr.co.yourplanet.online.business.project.controller;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.dto.request.ProjectAcceptForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectNegotiateForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRejectForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRequestForm;
import kr.co.yourplanet.online.business.project.dto.response.ProjectHistoryForm;
import kr.co.yourplanet.online.business.project.service.ProjectService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping(value = "/project", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseForm<String>> requestNewProject(@Valid @RequestPart ProjectRequestForm projectRequestForm, @RequestPart(required = false) List<MultipartFile> referenceFiles, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.requestProject(projectRequestForm, referenceFiles, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PutMapping("/project")
    public ResponseEntity<ResponseForm<String>> negotiateProject(@Valid @RequestBody ProjectNegotiateForm projectNegotiateForm, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.negotiateProject(projectNegotiateForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/project/reject")
    public ResponseEntity<ResponseForm<String>> rejectProject(@Valid @RequestBody ProjectRejectForm projectRejectForm, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.rejectProject(projectRejectForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/project/accept")
    public ResponseEntity<ResponseForm<String>> acceptProject(@Valid @RequestBody ProjectAcceptForm projectAcceptForm, @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.acceptProject(projectAcceptForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @GetMapping("/project/history")
    public ResponseEntity<ResponseForm<List<ProjectHistoryForm>>> getProjectHistoryList(@RequestParam("id") Long projectId, @AuthenticationPrincipal JwtPrincipal principal) {

        List<ProjectHistoryForm> projectHistoryFormList = projectService.getProjectHistoryList(projectId, principal.getId());

        ResponseForm<List<ProjectHistoryForm>> responseForm = new ResponseForm<>(StatusCode.OK, projectHistoryFormList);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public void getProjectDetail(@PathVariable(name = "id") Long projectId, @AuthenticationPrincipal JwtPrincipal principal) {

    }


}
