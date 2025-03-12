package kr.co.yourplanet.online.config;

import kr.co.yourplanet.online.jwt.JwtTokenProvider;
import kr.co.yourplanet.online.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    @Bean(name = "jwtTokenProvider")
    public JwtTokenProvider jwtTokenProvider(JwtProperties jwtProperties) {
        String secretKey = jwtProperties.getSecret(); // Base64 Encoded
        return new JwtTokenProvider(jwtProperties.getHeader(), secretKey, jwtProperties.getAccessTokenValidityTime(), jwtProperties.getRefreshTokenValidityTime());
    }

}
