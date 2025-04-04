package kr.co.yourplanet.online.business.user.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;
import lombok.Getter;

@Getter
public class BaseJoinForm {

    @ValidEnum(enumClass = MemberType.class)
    @Schema(defaultValue = "CREATOR")
    private MemberType memberType;

    @Schema(defaultValue = "BUSINESS")
    @ValidEnum(enumClass = BusinessType.class)
    private BusinessType businessType;

    @Schema(defaultValue = "hongildong@gmail.com")
    @NotBlank
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,3}$", message = "이메일 주소 양식을 확인해주세요.")
    private String email;

    @Schema(defaultValue = "password123@")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String tel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Valid
    @NotNull(message = "약관 동의여부를 확인해주세요.")
    private TermsForm termsForm;

    // Business
    @Valid
    private BusinessForm businessForm;

    @AssertTrue(message = "사업자 회원은 사업자 정보를 반드시 입력해야 합니다.")
    @JsonIgnore
    public boolean isValidBusinessRequest() {
        return !BusinessType.BUSINESS.equals(businessType) || businessForm != null;
    }
}