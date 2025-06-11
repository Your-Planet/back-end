package kr.co.yourplanet.online.business.auth.service.sender;

import org.springframework.stereotype.Component;

import kr.co.yourplanet.core.enums.AuthMethod;
import kr.co.yourplanet.online.business.alimtalk.util.BusinessAlimTalkSendService;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeSendCommand;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlimtalkAuthCodeSender implements AuthCodeSender {

    private final BusinessAlimTalkSendService alimTalkSendService;

    @Override
    public AuthMethod getMethod() {
        return AuthMethod.ALIMTALK;
    }

    @Override
    public void send(AuthCodeSendCommand command) {
        alimTalkSendService.sendAuthCode(command.memberId(), command.authCode());
    }
}
