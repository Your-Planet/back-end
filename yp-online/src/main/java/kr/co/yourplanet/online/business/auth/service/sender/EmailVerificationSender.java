package kr.co.yourplanet.online.business.auth.service.sender;

import org.springframework.stereotype.Component;

import kr.co.yourplanet.core.enums.AuthenticationMethod;
import kr.co.yourplanet.core.mail.EmailService;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeCommand;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailVerificationSender implements VerificationSender {

    private static final String SUBJECT = "[유어플래닛] 이메일 인증코드 안내";

    private final EmailService emailService;

    @Override
    public AuthenticationMethod getMethod() {
        return AuthenticationMethod.EMAIL;
    }

    @Override
    public void send(VerificationCodeCommand command) {
        emailService.send(command.destination(), SUBJECT, command.verificationCode());
    }
}