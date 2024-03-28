package kr.co.yourplanet.ypbackend.business.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Setter
@Getter
public class FindIdForm {
    @NotBlank
    private String name;
    @NotBlank
    private String phone;
}
