package kr.co.yourplanet.online.business.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.payment.controller.dto.request.PaymentApproveForm;
import kr.co.yourplanet.online.business.payment.controller.dto.request.PaymentRequestForm;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.business.payment.service.PaymentService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRequestService paymentRequestService;
    private final PaymentService paymentService;

    @PostMapping("/request")
    public ResponseEntity<ResponseForm<Void>> savePaymentRequest(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody PaymentRequestForm request
    ) {
        paymentRequestService.save(principal.getId(), request.orderId(), request.amount());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }

    @PostMapping("/approve")
    public ResponseEntity<ResponseForm<Void>> approvePayment(
            @AuthenticationPrincipal JwtPrincipal principal,
            @Valid @RequestBody PaymentApproveForm request
    ) {
        paymentService.approve(principal.getId(), request.projectId(), request.paymentKey(), request.orderId(), request.amount());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }
}
