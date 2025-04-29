package kr.co.yourplanet.core.entity.payment;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PaymentHistory extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_history_seq")
    @SequenceGenerator(name = "payment_history_seq", sequenceName = "payment_history_seq", allocationSize = 50)
    private Long id;

    @Column(nullable = false)
    private String paymentKey;

    @Column(unique = true, nullable = false)
    private String orderId;

    private String orderName;

    private String method;

    private Long totalAmount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String reason;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentProvider provider;

    @Column(name = "provider_status")
    private String providerStatus;

    @Column(nullable = false)
    private String providerResponse;

    @Column(name = "target_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Long targetId;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
}
