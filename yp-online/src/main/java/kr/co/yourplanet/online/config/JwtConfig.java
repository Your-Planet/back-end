package kr.co.yourplanet.online.config;

import kr.co.yourplanet.online.jwt.JwtProperties;
import kr.co.yourplanet.online.jwt.JwtTokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfig {

    @Bean(name = "jwtTokenProvider")
    public JwtTokenProvider jwtTokenProvider(JwtProperties jwtProperties) {
        String secretKey = Base64.getEncoder().encodeToString(jwtProperties.getSecret().getBytes()); // Base64 Encoding
        return new JwtTokenProvider(jwtProperties.getHeader(), secretKey, jwtProperties.getTokenValidityTime());
    }

}
