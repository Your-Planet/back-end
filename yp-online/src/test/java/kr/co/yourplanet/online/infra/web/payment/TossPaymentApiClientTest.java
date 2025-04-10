package kr.co.yourplanet.online.infra.web.payment;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.yourplanet.core.entity.payment.PaymentProvider;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentRequest;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.support.template.IntegrationTest;
import okhttp3.mockwebserver.MockResponse;

class TossPaymentApiClientTest extends IntegrationTest {

    @Autowired
    TossPaymentApiClient tossPaymentApiClient;

    @Nested
    @DisplayName("Toss 결제 승인 API mock 응답 처리 테스트")
    class Approve {

        @Test
        @DisplayName("[성공] 결제 승인 API의 성공 응답 처리를 검증한다")
        void success() throws JsonProcessingException {
            // given
            int statusCode = 200;
            PaymentResponse.SuccessResponse dto = PaymentResponse.SuccessResponse.builder()
                    .orderName("샘플결제")
                    .status("DONE")
                    .method("카드")
                    .totalAmount(1000L)
                    .build();

            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(statusCode)
                    .setHeader("Content-Type", "application/json")
                    .setBody(toJson(dto)));

            PaymentRequest request = PaymentRequest.builder()
                    .paymentKey("pay-test-1")
                    .orderId("order-123")
                    .amount(1000L)
                    .build();

            // when
            PaymentResponse response = tossPaymentApiClient.process(request, "test-idempotency");

            // then
            assertThat(response).isNotNull();
            assertThat(response.isSuccess()).isTrue();
            assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getProvider()).isEqualTo(PaymentProvider.TOSS);
            assertThat(response.getPaymentKey()).isEqualTo(request.paymentKey());
            assertThat(response.getOrderId()).isEqualTo(request.orderId());
            assertThat(response.getHttpStatusCode().value()).isEqualTo(statusCode);
            assertThat(response.getSuccessResponse().getStatus()).isEqualTo(dto.getStatus());
            assertThat(response.getSuccessResponse().getOrderName()).isEqualTo(dto.getOrderName());
        }

        @Test
        @DisplayName("[성공] 결제 승인 API의 실패 응답 처리를 검증한다")
        void fail() throws JsonProcessingException {
            // given
            int statusCode = 500;
            PaymentResponse.FailResponse dto = PaymentResponse.FailResponse.builder()
                    .code("INTERNAL_SERVER_ERROR")
                    .message("서버 오류 발생")
                    .build();

            mockWebServer.enqueue(new MockResponse()
                    .setResponseCode(statusCode)
                    .setHeader("Content-Type", "application/json")
                    .setBody(toJson(dto)));

            PaymentRequest request = PaymentRequest.builder()
                    .paymentKey("pay-test-2")
                    .orderId("order-500")
                    .amount(2000L)
                    .build();

            // when
            PaymentResponse response = tossPaymentApiClient.process(request, "test-idempotency");

            // then
            assertThat(response).isNotNull();
            assertThat(response.isSuccess()).isFalse();
            assertThat(response.getHttpStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
            assertThat(response.getProvider()).isEqualTo(PaymentProvider.TOSS);
            assertThat(response.getPaymentKey()).isEqualTo(request.paymentKey());
            assertThat(response.getOrderId()).isEqualTo(request.orderId());
            assertThat(response.getFailResponse().getCode()).isEqualTo("INTERNAL_SERVER_ERROR");
            assertThat(response.getFailResponse().getMessage()).isEqualTo("서버 오류 발생");
        }
    }
}