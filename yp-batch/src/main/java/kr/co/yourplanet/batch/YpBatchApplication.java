package kr.co.yourplanet.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "kr.co.yourplanet")
@EnableScheduling
@EnableAsync
@EntityScan(basePackages = "kr.co.yourplanet")
@EnableJpaRepositories(basePackages = "kr.co.yourplanet")
public class YpBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(YpBatchApplication.class, args);
	}
}
