package kr.co.yourplanet.online.business.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Setter
@Getter
public class FindIdForm {
    @NotBlank(message = "이름을 입력해 주세요")
    private String name;
    @NotBlank(message = "전화번호를 입력해주세요")
    private String tel;
}
