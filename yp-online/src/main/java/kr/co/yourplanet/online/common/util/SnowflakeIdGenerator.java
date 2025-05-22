package kr.co.yourplanet.online.common.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SnowflakeIdGenerator {

	private static final Random RANDOM = new Random(); // Random 객체 재사용
	private static final String BASE32_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUV";

	private static final int MILLIS_OF_DAY_MASK = 0x07FFFFFF; // 27비트

	private static final long WORKER_ID_BITS = 4;
	private static final long RANDOM_BITS = 14;
	private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

	private final long workerId;

	public SnowflakeIdGenerator(long workerId) {
		if (workerId > MAX_WORKER_ID || workerId < 0) {
			log.error("Worker ID out of range : " + workerId);
			throw new IllegalArgumentException("Worker ID out of range");
		}
		this.workerId = workerId;
	}

	// 유니크 ID 채번 메소드
	public String generateId(String prefix) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalDateTime startOfDay = currentDateTime.toLocalDate().atStartOfDay();
		long millisOfDay = Duration.between(startOfDay, currentDateTime).toMillis();

		long id = generate45BitsId(millisOfDay);
		String ymd = currentDateTime.format(DateTimeFormatter.ofPattern("yyMMdd"));

		return prefix + ymd + convertLow45BitsToBase32(id);
	}

	// 45bits 만들기
	private long generate45BitsId(long millisOfDay) {
		int timeId = (int)(millisOfDay & MILLIS_OF_DAY_MASK); // 27비트 추출
		int randomId = RANDOM.nextInt(1 << 18); // 18비트 랜덤 값
		return ((long)timeId << WORKER_ID_BITS + RANDOM_BITS)
			| (workerId << RANDOM_BITS)
			| randomId; // 시분초(27) + 워커ID(4) + 랜덤(14) = 45비트
	}

	private String convertLow45BitsToBase32(long value) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < 9; i++) { // 5비트씩 총 9번 추출
			int index = (int)(value & 0x1F); // 하위 5비트 추출
			result.append(BASE32_ALPHABET.charAt(index)); // Base32 문자 매핑
			value >>= 5; // 5비트 이동
		}
		return result.reverse().toString(); // Base32는 큰 자리수가 앞에 오도록 Reverse
	}

}
