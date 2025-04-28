package kr.co.yourplanet.online.business.payment.service.dto;

import org.springframework.http.HttpStatusCode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.entity.payment.PaymentProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class PaymentResponse {
    @NotNull
    private boolean isSuccess;
    @NotNull
    private HttpStatusCode httpStatusCode;
    @NotNull
    private PaymentProvider provider;
    @NotBlank
    private String paymentKey;
    @NotBlank
    private String orderId;
    private SuccessResponse successResponse;
    private FailResponse failResponse;
    private String providerResponse;

    @Builder
    @Getter
    public static class SuccessResponse {
        private String orderName;
        private String status;
        private String method;
        private Long totalAmount;
    }

    @Builder
    @Getter
    public static class FailResponse {
        private String code;
        private String message;
    }

    public static PaymentResponse success(HttpStatusCode code, SuccessResponse response, PaymentProvider provider, String paymentKey, String orderId,  String responseJson) {
        return PaymentResponse.builder()
                .isSuccess(true)
                .httpStatusCode(code)
                .successResponse(response)
                .provider(provider)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .providerResponse(responseJson)
                .build();
    }

    public static PaymentResponse fail(HttpStatusCode code, FailResponse response, PaymentProvider provider, String paymentKey, String orderId,  String responseJson) {
        return PaymentResponse.builder()
                .isSuccess(false)
                .httpStatusCode(code)
                .failResponse(response)
                .provider(provider)
                .paymentKey(paymentKey)
                .orderId(orderId)
                .providerResponse(responseJson)
                .build();
    }
}