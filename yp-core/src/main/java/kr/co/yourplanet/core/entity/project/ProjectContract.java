package kr.co.yourplanet.core.entity.project;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ProjectContract {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_contract_seq")
    @SequenceGenerator(name = "project_contract_seq", sequenceName = "project_contract_seq", allocationSize = 50)
    private Long id;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Comment("의뢰 수락일 (오늘)")
    private LocalDateTime acceptDateTime;

    @Comment("의뢰 마감일 (작업 기한)")
    private LocalDate dueDate;

    @Comment("거래 금액")
    private Long contractAmount;

    /*
     * 계약 내용
     */
    @Comment("디자인 수요자 정보")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "client_company_name")),
            @AttributeOverride(name = "registrationNumber", column = @Column(name = "client_registration_number")),
            @AttributeOverride(name = "address", column = @Column(name = "client_address")),
            @AttributeOverride(name = "representativeName", column = @Column(name = "client_representative_name"))
    })
    private Contractor client;

    @Comment("디자인 공급자 정보")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "provider_company_name")),
            @AttributeOverride(name = "registrationNumber", column = @Column(name = "provider_registration_number")),
            @AttributeOverride(name = "address", column = @Column(name = "provider_address")),
            @AttributeOverride(name = "representativeName", column = @Column(name = "provider_representative_name"))
    })
    private Contractor provider;

    @Comment("작가 작성일")
    private LocalDateTime providerWrittenDateTime;

    @Comment("광고주 작성일")
    private LocalDateTime clientWrittenDateTime;

    @Comment("계약 완료일")
    private LocalDateTime completeDateTime;

    public void writeClientInfo(Contractor client) {
        this.client = client;
        this.clientWrittenDateTime = LocalDateTime.now();
    }

    public void writeProviderInfo(Contractor provider) {
        this.provider = provider;
        this.providerWrittenDateTime = LocalDateTime.now();
    }

    public void completeContract() {
        this.completeDateTime = LocalDateTime.now();
    }

    public boolean isCompleted() {
        return isClientWritten() && isProviderWritten();
    }

    public boolean isClientWritten() {
        return clientWrittenDateTime != null;
    }

    public boolean isProviderWritten() {
        return providerWrittenDateTime != null;
    }
}
