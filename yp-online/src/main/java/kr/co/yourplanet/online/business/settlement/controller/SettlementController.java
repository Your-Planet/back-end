package kr.co.yourplanet.online.business.settlement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.yourplanet.core.entity.settlement.SettlementPaymentStatus;
import kr.co.yourplanet.core.entity.settlement.SettlementStatus;
import kr.co.yourplanet.core.enums.StatusCode;
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
}
