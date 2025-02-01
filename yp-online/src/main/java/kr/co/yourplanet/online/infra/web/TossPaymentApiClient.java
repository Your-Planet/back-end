package kr.co.yourplanet.online.infra.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.yourplanet.core.entity.payment.PaymentProvider;
import kr.co.yourplanet.online.business.payment.service.PaymentClient;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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
                .header(AUTHORIZATION, String.format(AUTHORIZATION_FORMAT, secretKey))
                .header("Idempotency-Key", idempotencyKey)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange((req, res) -> {
                            HttpStatusCode code = res.getStatusCode();
                            String responseJson = objectMapper.readTree(res.getBody()).toString();

                            if (code.is2xxSuccessful()) {
                                PaymentResponse.SuccessResponse response = objectMapper.readValue(res.getBody(), PaymentResponse.SuccessResponse.class);

                                return PaymentResponse.success(
                                        res.getStatusCode(),
                                        response,
                                        PaymentProvider.TOSS,
                                        request.paymentKey(),
                                        request.orderId(),
                                        responseJson
                                );
                            } else {
                                PaymentResponse.FailResponse response = objectMapper.readValue(res.getBody(), PaymentResponse.FailResponse.class);
                                return PaymentResponse.fail(
                                        res.getStatusCode(),
                                        response,
                                        PaymentProvider.TOSS,
                                        request.paymentKey(),
                                        request.orderId(),
                                        responseJson
                                );
                            }
                        }
                );
    }
}