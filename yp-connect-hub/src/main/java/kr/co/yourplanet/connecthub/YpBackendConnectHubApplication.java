package kr.co.yourplanet.connecthub;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"kr.co.yourplanet"})
public class YpBackendConnectHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(YpBackendConnectHubApplication.class, args);
    }

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
