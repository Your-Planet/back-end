package kr.co.yourplanet.core.mail;

public interface EmailService {

    void send(String to, String subject, String body);
}
