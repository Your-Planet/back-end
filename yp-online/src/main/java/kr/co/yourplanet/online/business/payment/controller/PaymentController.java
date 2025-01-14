package kr.co.yourplanet.online.business.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.payment.dto.request.PaymentRequestForm;
import kr.co.yourplanet.online.business.payment.service.PaymentRequestService;
import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.online.jwt.JwtPrincipal;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRequestService paymentRequestService;

    @PostMapping("/request")
    public ResponseEntity<ResponseForm<Void>> savePaymentRequest(
            @AuthenticationPrincipal JwtPrincipal principal,
            @RequestBody PaymentRequestForm request
    ) {
        paymentRequestService.savePaymentRequest(principal.getId(), request.orderId(), request.amount());

        return new ResponseEntity<>(new ResponseForm<>(StatusCode.OK), HttpStatus.OK);
    }
}
