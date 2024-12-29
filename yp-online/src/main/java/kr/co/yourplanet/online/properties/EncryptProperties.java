package kr.co.yourplanet.online.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "encrypt")
public class EncryptProperties {

    private final String secret;
}
