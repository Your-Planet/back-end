package kr.co.yourplanet.core.entity.settlement;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.util.FeeCalculator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class ProjectSettlement extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_settlement_seq")
    @SequenceGenerator(name = "project_settlement_seq", sequenceName = "project_settlement_seq", allocationSize = 10)
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "payment_amount", nullable = false)
    private Long paymentAmount;

    @Column(name = "settlement_amount", nullable = false)
    private Long settlementAmount;

    @Column(name = "fee", nullable = false)
    private Long fee;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "settlement_date")
    private LocalDateTime settlementDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private SettlementPaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "settlement_status", nullable = false)
    private SettlementStatus settlementStatus;

    public static ProjectSettlement create(Project project, long paymentAmount, SettlementStatus settlementStatus) {
        long fee = FeeCalculator.calculateFee(paymentAmount, 10);
        long settlementAmount = paymentAmount - fee;

        return ProjectSettlement.builder()
                .project(project)
                .paymentAmount(paymentAmount)
                .settlementAmount(settlementAmount)
                .fee(fee)
                .paymentStatus(SettlementPaymentStatus.PAYMENT_PENDING)
                .settlementStatus(settlementStatus)
                .build();
    }

    public void completePayment(LocalDateTime paymentDate) {
        this.paymentStatus = SettlementPaymentStatus.PAYMENT_COMPLETED;
        this.paymentDate = paymentDate;
    }

    public boolean isSettlementCompleted() {
        return SettlementStatus.SETTLEMENT_COMPLETED.equals(this.settlementStatus);
    }
}
