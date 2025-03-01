package kr.co.yourplanet.online.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.yourplanet.online.common.util.SnowflakeIdGenerator;

@Configuration
public class SnowflakeConfig {
	@Bean
	public SnowflakeIdGenerator snowflakeIdGenerator() {
		return new SnowflakeIdGenerator(1); //
	}
}