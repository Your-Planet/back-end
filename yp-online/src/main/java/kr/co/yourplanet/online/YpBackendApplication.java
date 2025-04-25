package kr.co.yourplanet.online;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

import jakarta.annotation.PostConstruct;

@SpringBootApplication(scanBasePackages = "kr.co.yourplanet")
@EnableAsync
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"kr.co.yourplanet"})
@EnableJpaRepositories(basePackages = "kr.co.yourplanet")
public class YpBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(YpBackendApplication.class, args);
    }

    @PostConstruct
    public void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }


}
