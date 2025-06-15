package kr.co.yourplanet.online.business.auth.service.sender;

import kr.co.yourplanet.core.enums.AuthMethod;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeSendCommand;

public interface AuthCodeSender {

    AuthMethod getMethod();
    void send(AuthCodeSendCommand command);
}
