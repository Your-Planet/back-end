package kr.co.yourplanet.online.business.user.dto.update;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SettlementForm {

    @Schema(defaultValue = "BUSINESS")
    @ValidEnum(enumClass = BusinessType.class)
    private BusinessType businessType;

    @NotBlank(message = "은행명은 비어있을 수 없습니다.")
    private String bankName;

    @NotBlank(message = "은행명은 비어있을 수 없습니다.")
    private String accountHolder;

    @NotBlank(message = "은행명은 비어있을 수 없습니다.")
    private String accountNumber;

    @Schema(defaultValue = "https://naver.com")
    @URL(message = "유효한 URL 형식이 아닙니다.")
    private String bankAccountCopyUrl;

    @Schema(defaultValue = "https://naver.com")
    @URL(message = "유효한 URL 형식이 아닙니다.")
    private String businessLicenseUrl;

    @Schema(defaultValue = "123456-1234567")
    @Pattern(regexp = "^\\d{6}-\\d{7}$")
    private String rrn;


    @AssertTrue(message = "사업자 회원은 사업자 정산 정보를 반드시 입력해야 합니다.")
    @JsonIgnore
    public boolean isBusinessSettlement() {
        if (BusinessType.BUSINESS.equals(businessType)) {
            return bankAccountCopyUrl != null && businessLicenseUrl != null;
        }
        return true;
    }

    @AssertTrue(message = "개인 회원은 개인 정산 정보를 반드시 입력해야 합니다.")
    @JsonIgnore
    public boolean isIndividualSettlement() {
        if (BusinessType.INDIVIDUAL.equals(businessType)) {
            return rrn != null;
        }
        return true;
    }
}
