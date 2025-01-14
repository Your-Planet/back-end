package kr.co.yourplanet.online.business.payment.dto.request;

public record PaymentRequestForm(
        String orderId,
        Long amount
) {
}
