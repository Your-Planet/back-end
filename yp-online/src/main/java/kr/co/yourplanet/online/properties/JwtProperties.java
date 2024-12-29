package kr.co.yourplanet.online.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String header;
    private final String secret;
    private final long accessTokenValidityTime;
    private final long refreshTokenValidityTime;

}
