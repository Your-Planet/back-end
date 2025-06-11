package kr.co.yourplanet.online.business.settlement.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSettlementDetailInfo;
import kr.co.yourplanet.online.business.settlement.dto.ProjectSettlementSummariesInfo;
import kr.co.yourplanet.online.business.settlement.service.ProjectSettlementQueryService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;

@Tag(name = "Settlement", description = "정산 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/settlement")
public class SettlementController {

    private final ProjectSettlementQueryService projectSettlementQueryService;

    @Operation(summary = "상태 별 작가 프로젝트 정산 건수 조회", description = "정산 상태 필수")
    @GetMapping("/project/count")
    public ResponseEntity<ResponseForm<Long>> getSettlementCountByStatus(
            @AuthenticationPrincipal JwtPrincipal jwtPrincipal,
            @RequestParam(required = false) SettlementPaymentStatus paymentStatus,
            @RequestParam SettlementStatus settlementStatus
    ) {
        long count = projectSettlementQueryService.countByStatus(jwtPrincipal.getId(), paymentStatus, settlementStatus);
        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, count), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "프로젝트 정산 정보 목록 조회")
    @GetMapping("/project")
    public ResponseEntity<ResponseForm<ProjectSettlementSummariesInfo>> getProjectSettlements(
            Pageable pageable
    ) {
        ProjectSettlementSummariesInfo response = projectSettlementQueryService.getSummariesInfo(pageable);
        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, response), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "프로젝트 정산 상세 정보 조회")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseForm<ProjectSettlementDetailInfo>> getProjectSettlementDetail(
            @PathVariable Long projectId
    ) {
        ProjectSettlementDetailInfo response = projectSettlementQueryService.getDetailInfo(projectId);
        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK, response), HttpStatus.OK);
    }
}
