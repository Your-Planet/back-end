package kr.co.yourplanet.online.business.auth.service.sender;

import kr.co.yourplanet.core.enums.AuthenticationMethod;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeCommand;

public interface VerificationSender {

    AuthenticationMethod getMethod();
    void send(VerificationCodeCommand command);
}
