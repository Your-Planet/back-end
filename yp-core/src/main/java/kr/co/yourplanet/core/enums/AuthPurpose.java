package kr.co.yourplanet.core.enums;

import java.util.Set;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthPurpose {

    RESET_PASSWORD(Set.of(AuthMethod.EMAIL, AuthMethod.ALIMTALK)),
    FIND_EMAIL(Set.of(AuthMethod.ALIMTALK));

    private final Set<AuthMethod> availableMethods;

    public boolean supports(AuthMethod method) {
        return availableMethods.contains(method);
    }
}
