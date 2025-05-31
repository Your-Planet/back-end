package kr.co.yourplanet.online.infra.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.mail.EmailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GmailService implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
