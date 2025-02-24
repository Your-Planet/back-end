package kr.co.yourplanet.online.business.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusinessForm(

        @NotNull(message = "상호명은 null일 수 없습니다.")
        String companyName,

        @NotNull(message = "사업자등록번호는 null일 수 없습니다.")
        String businessNumber,

        @NotNull(message = "대표자명은 null일 수 없습니다.")
        String representativeName,

        @NotNull(message = "사업장 주소는 null일 수 없습니다.")
        String businessAddress,

        String businessAddressDetail
) {
}
