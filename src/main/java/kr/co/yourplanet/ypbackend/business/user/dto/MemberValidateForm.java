package kr.co.yourplanet.ypbackend.business.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberValidateForm {
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @NotBlank(message = "전화번호를 입력해주세요")
    private String tel;
}
