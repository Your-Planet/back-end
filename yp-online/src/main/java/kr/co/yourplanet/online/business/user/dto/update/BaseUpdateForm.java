package kr.co.yourplanet.online.business.user.dto.update;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.ValidEnum;
import kr.co.yourplanet.online.business.user.dto.BusinessForm;
import lombok.Getter;

@Getter
public class BaseUpdateForm {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String tel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Schema(defaultValue = "BUSINESS")
    @ValidEnum(enumClass = BusinessType.class)
    private BusinessType businessType;

    @Valid
    private BusinessForm businessForm;

    @AssertTrue(message = "사업자 회원은 사업자 정보를 반드시 입력해야 합니다.")
    @JsonIgnore
    public boolean isBusinessValid() {
        if (BusinessType.BUSINESS.equals(businessType)) {
            return businessForm != null;
        }
        return true;
    }
}
