package kr.co.yourplanet.online.business.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.dto.request.ContractDraftForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectAcceptForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectNegotiateForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRejectForm;
import kr.co.yourplanet.online.business.project.dto.request.ProjectRequestForm;
import kr.co.yourplanet.online.business.project.dto.response.ProjectBasicInfo;
import kr.co.yourplanet.online.business.project.dto.response.ProjectDetailInfo;
import kr.co.yourplanet.online.business.project.dto.response.ProjectHistoryForm;
import kr.co.yourplanet.online.business.project.dto.response.ContractInfo;
import kr.co.yourplanet.online.business.project.service.ContractDraftService;
import kr.co.yourplanet.online.business.project.service.ProjectService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;

@Tag(name = "Project", description = "프로젝트 API")
@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ContractDraftService contractDraftService;

    @PostMapping(value = "/project", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseForm<String>> createProject(@Valid @RequestPart ProjectRequestForm projectRequestForm,
            @RequestPart(required = false) List<MultipartFile> referenceFiles,
            @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.createProject(projectRequestForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PutMapping("/project")
    public ResponseEntity<ResponseForm<String>> negotiateProject(
            @Valid @RequestBody ProjectNegotiateForm projectNegotiateForm,
            @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.negotiateProject(projectNegotiateForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/project/reject")
    public ResponseEntity<ResponseForm<String>> rejectProject(@Valid @RequestBody ProjectRejectForm projectRejectForm,
            @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.rejectProject(projectRejectForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/project/accept")
    public ResponseEntity<ResponseForm<String>> acceptProject(@Valid @RequestBody ProjectAcceptForm projectAcceptForm,
            @AuthenticationPrincipal JwtPrincipal principal) {

        projectService.acceptProject(projectAcceptForm, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @GetMapping("/project/history")
    public ResponseEntity<ResponseForm<List<ProjectHistoryForm>>> getProjectHistoryList(
            @RequestParam("id") Long projectId, @AuthenticationPrincipal JwtPrincipal principal) {

        List<ProjectHistoryForm> projectHistoryFormList = projectService.getProjectHistoryList(projectId,
                principal.getId());

        ResponseForm<List<ProjectHistoryForm>> responseForm = new ResponseForm<>(StatusCode.OK, projectHistoryFormList);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    @GetMapping("/project")
    public ResponseEntity<ResponseForm<List<ProjectBasicInfo>>> getMemberProjectsBasicInfo(
            @AuthenticationPrincipal JwtPrincipal principal) {
        List<ProjectBasicInfo> memberProjectBasicInfoList = projectService.getMemberProjectsBasicInfo(
                principal.getId());

        ResponseForm<List<ProjectBasicInfo>> responseForm = new ResponseForm<>(StatusCode.OK,
                memberProjectBasicInfoList);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ResponseForm<ProjectDetailInfo>> getProjectDetailInfo(
            @PathVariable(name = "id") Long projectId, @AuthenticationPrincipal JwtPrincipal principal) {
        ProjectDetailInfo projectDetailInfo = projectService.getProjectDetailInfo(projectId, principal.getId());
        ResponseForm<ProjectDetailInfo> responseForm = new ResponseForm<>(StatusCode.OK, projectDetailInfo);

        return new ResponseEntity<>(responseForm, HttpStatus.OK);
    }

    @Operation(summary = "계약서 조회")
    @GetMapping("/project/{id}/contract")
    public ResponseEntity<ResponseForm<ContractInfo>> getContract(
            @AuthenticationPrincipal JwtPrincipal principal,
            @PathVariable(name = "id") Long projectId
    ) {
        ContractInfo response = contractDraftService.getContract(projectId, principal.getId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, response), HttpStatus.OK);
    }

    @Operation(summary = "계약서 작성")
    @PostMapping("/project/{id}/contract")
    public ResponseEntity<ResponseForm<Void>> draftContract(
            @AuthenticationPrincipal JwtPrincipal principal,
            @PathVariable(name = "id") Long projectId,
            @RequestBody @Valid ContractDraftForm request
    ) {
        contractDraftService.draftContract(projectId, principal.getId(), request);

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.CREATED), HttpStatus.CREATED);
    }
}