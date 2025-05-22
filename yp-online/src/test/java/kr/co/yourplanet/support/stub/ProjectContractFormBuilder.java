package kr.co.yourplanet.support.stub;

import kr.co.yourplanet.online.business.project.dto.request.ContractDraftForm;

public class ProjectContractFormBuilder {

    public static ContractDraftForm businessContractDraftForm() {
        return ContractDraftForm.builder()
                .companyName("Your Planet")
                .identificationNumber("123-12-12345")
                .address("서울특별시 강남구 테헤란로 123")
                .representativeName("사업자 대표자 이름")
                .build();
    }

    public static ContractDraftForm individualContractDraftForm() {
        return ContractDraftForm.builder()
                .identificationNumber("123456-1234567")
                .address("서울특별시 서초구 반포대로 58")
                .representativeName("개인 대표자 이름")
                .build();
    }
}
