package kr.co.yourplanet.online.business.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.payment.controller.dto.request.PaymentApproveForm;
import kr.co.yourplanet.online.business.payment.controller.dto.request.PaymentRequestForm;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.business.payment.service.PaymentService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;

@Tag(name = "Payment", description = "결제 API")
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRequestService paymentRequestService;
    private final PaymentService paymentService;

    @Operation(summary = "결제 요청")
    @PostMapping("/request")
    public ResponseEntity<ResponseForm<Void>> savePaymentRequest(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody PaymentRequestForm request
    ) {
        paymentRequestService.save(principal.getId(), request.orderId(), request.amount());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @Operation(summary = "결제 승인")
    @PostMapping("/approve")
    public ResponseEntity<ResponseForm<Void>> approvePayment(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody PaymentApproveForm request
    ) {
        paymentService.approve(request.paymentType(), principal.getId(), request.paymentKey(), request.orderId(), request.amount(), request.targetId());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }
}
