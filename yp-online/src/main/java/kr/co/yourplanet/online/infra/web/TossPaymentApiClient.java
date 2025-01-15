package kr.co.yourplanet.online.infra.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import kr.co.yourplanet.online.business.payment.domain.exception.PaymentFailureException;
import kr.co.yourplanet.online.business.payment.service.PaymentClient;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TossPaymentApiClient implements PaymentClient {

    private static final String AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Basic ";

    private final RestClient restClient;

    @Value("${payments.toss.uri}")
    private String uri;

    @Value("${payments.toss.secret-key}")
    private String secretKey;

    @Override
    public PaymentResponse process(PaymentRequest request, String idempotencyKey) {
        return restClient.post()
                .uri(uri)
                .body(request)
                .header(AUTHORIZATION, AUTHORIZATION_PREFIX + secretKey)
                .header("Idempotency-Key", idempotencyKey)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new PaymentFailureException("", request.paymentKey(), request.orderId());
                })
                .body(PaymentResponse.class);
    }
}
