package kr.co.yourplanet.ypbackend.business.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LoginForm {

    private String id;
    private String password;
}
