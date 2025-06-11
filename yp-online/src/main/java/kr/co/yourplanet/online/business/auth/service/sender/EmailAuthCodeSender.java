package kr.co.yourplanet.online.business.auth.service.sender;

import org.springframework.stereotype.Component;

import kr.co.yourplanet.core.enums.AuthMethod;
import kr.co.yourplanet.core.mail.EmailService;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeSendCommand;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailAuthCodeSender implements AuthCodeSender {

    private static final String SUBJECT = "[유어플래닛] 이메일 인증코드 안내";

    private final EmailService emailService;

    @Override
    public AuthMethod getMethod() {
        return AuthMethod.EMAIL;
    }

    @Override
    public void send(AuthCodeSendCommand command) {
        emailService.send(command.destination(), SUBJECT, command.authCode());
    }
}