package kr.co.yourplanet.online.business.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import kr.co.yourplanet.core.validation.annotation.ValidIdentificationNumber;
import lombok.Builder;

@Builder
public record ContractDraftForm(

        @Schema(description = "상호 또는 명칭", nullable = true)
        String companyName,

        @Schema(description = "사업자 번호 또는 주민등록번호", defaultValue = "123-12-12345")
        @NotEmpty(message = "사업자 번호 또는 주민등록번호는 비어있을 수 없습니다.")
        @ValidIdentificationNumber
        String identificationNumber,

        @Schema(description = "주소")
        @NotEmpty(message = "주소는 비어있을 수 없습니다.")
        String address,

        @Schema(description = "대표자명")
        @NotEmpty(message = "대표자명은 비어있을 수 없습니다.")
        String representativeName
) {
}
