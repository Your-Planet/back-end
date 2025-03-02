package kr.co.yourplanet.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"kr.co.yourplanet"})
public class YpBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(YpBatchApplication.class, args);
	}
}
