package kr.co.yourplanet.online.business.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;
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

    @NotBlank(message = "예금주명은 비어있을 수 없습니다.")
    private String accountHolder;

    @NotBlank(message = "계좌번호는 비어있을 수 없습니다.")
    private String accountNumber;

    private Long bankAccountCopyFileId;

    private Long businessLicenseFileId;

    @Schema(defaultValue = "123456-1234567")
    @Pattern(regexp = "^\\d{6}-\\d{7}$", message = "유효한 주민등록번호 형식이 아닙니다.")
    private String rrn;
}
