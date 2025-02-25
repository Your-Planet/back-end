package kr.co.yourplanet.online.business.project.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record ContractDraftForm(

        String companyName,

        @NotEmpty(message = "사업자 번호 또는 주민등록번호는 비어있을 수 없습니다.")
        String registrationNumber,

        @NotEmpty(message = "주소는 비어있을 수 없습니다.")
        String address,

        @NotEmpty(message = "대표자명은 비어있을 수 없습니다.")
        String representativeName
) {
}
