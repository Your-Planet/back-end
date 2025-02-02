package kr.co.yourplanet.batch;

import kr.co.yourplanet.batch.job.AlimTalkTask;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
@EntityScan(basePackages = {"kr.co.yourplanet"})
public class YpBatchApplication {
	public static void main(String[] args) {
		SpringApplication.run(YpBatchApplication.class, args);
	}

}
