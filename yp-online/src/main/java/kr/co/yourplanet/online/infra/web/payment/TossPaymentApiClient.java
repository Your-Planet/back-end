package kr.co.yourplanet.online.infra.web.payment;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.yourplanet.core.entity.payment.PaymentProvider;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.payment.service.PaymentClient;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TossPaymentApiClient implements PaymentClient {

    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_FORMAT = "Basic %s";

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${payments.toss.uri}")
    private String uri;

    @Value("${payments.toss.secret-key}")
    private String secretKey;

    @Override
    public PaymentResponse process(PaymentRequest request, String idempotencyKey) {
        return restClient.post()
                .uri(uri)
                .body(request)
                .header(AUTHORIZATION, String.format(AUTHORIZATION_FORMAT, encodeSecretKey()))
                .header("Idempotency-Key", idempotencyKey)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange((req, res) -> handleResponse(request, res));
    }

    private PaymentResponse handleResponse(PaymentRequest request, ClientHttpResponse res) {
        try {
            HttpStatusCode code = res.getStatusCode();
            String responseJson = objectMapper.readTree(res.getBody()).toString();

            if (code.is2xxSuccessful()) {
                return handleSuccessResponse(code, request, responseJson);
            } else {
                return handleFailResponse(code, request, responseJson);
            }
        } catch (IOException e) {
            log.error("[PAYMENT] 응답 파싱 실패 (paymentKey= {})", request.paymentKey());
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "응답을 파싱하는데 실패했습니다.", false);
        }
    }

    private PaymentResponse handleSuccessResponse(HttpStatusCode code, PaymentRequest request, String json) throws JsonProcessingException {
        PaymentResponse.SuccessResponse response = objectMapper.readValue(json, PaymentResponse.SuccessResponse.class);

        return PaymentResponse.success(
                code,
                response,
                PaymentProvider.TOSS,
                request.paymentKey(),
                request.orderId(),
                json
        );
    }

    private PaymentResponse handleFailResponse(HttpStatusCode code, PaymentRequest request, String json) throws JsonProcessingException {
        PaymentResponse.FailResponse response = objectMapper.readValue(json, PaymentResponse.FailResponse.class);

        if (TossPaymentErrorCode.isValueIn(response.getCode())) {
            log.error("[PAYMENT] {} : {}", response.getCode(), response.getMessage());
        }

        return PaymentResponse.fail(
                code,
                response,
                PaymentProvider.TOSS,
                request.paymentKey(),
                request.orderId(),
                json
        );
    }

    private String encodeSecretKey() {
        return Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
    }
}