package kr.co.yourplanet.online.business.user.dto.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
}
