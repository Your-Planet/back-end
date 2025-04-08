package kr.co.yourplanet.core.alimtalk.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkAuth;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendResponseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlimTalkApiClient {
    private final RedisTemplate<String, String> redisTemplate;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    private static final String OMNI_SUCCESS = "A000";
    private static final String SCHEMA_KEY = "schema";
    private static final String TOKEN_KEY = "token";
    private static final String EXPIRED_KEY = "expired";

    @Value("${omni.id}")
    private String id;
    @Value("${omni.password}")
    private String password;
    @Value("${omni.host}")
    private String host;
    @Value("${omni.url.auth}")
    private String authUrl;
    @Value("${omni.url.alimtalk}")
    private String sendUrl;

    /**
     * 알림톡 API 호출 (외부 통신 전용)
     */
    public AlimTalkSendResponseForm sendAlimTalk(AlimTalkSendForm alimTalkSendForm) throws
        JsonProcessingException {
        String authToken = getAuthToken();
        String json = objectMapper.writeValueAsString(alimTalkSendForm);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
            .url(String.format("%s%s", host, sendUrl))
            .post(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", authToken)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return objectMapper.readValue(response.body().string(), AlimTalkSendResponseForm.class);
            } else {
                log.error("알림톡 전송 실패: HTTP {} - {}", response.code(), response.message());
                return new AlimTalkSendResponseForm(String.valueOf(response.code()), "알림톡 전송 실패", "", "");
            }
        } catch (Exception e) {
            log.error("알림톡 전송 중 예외 발생: {}", e.getMessage(), e);
            return new AlimTalkSendResponseForm("E999", "알림톡 전송 중 예외 발생", "", "");
        }
    }

    /**
     * 토큰 갱신
     */
    public boolean getNewAuth() {
        RequestBody emptyBody = RequestBody.create("", MediaType.get("application/json"));

        Request request = new Request.Builder()
            .url(String.format("%s%s", host, authUrl))
            .method("POST", emptyBody)
            .addHeader("Content-Type", "application/json")
            .addHeader("X-IB-Client-Id", this.id)
            .addHeader("X-IB-Client-Passwd", this.password)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                AlimTalkAuth alimTalkAuth = objectMapper.readValue(response.body().string(), AlimTalkAuth.class);
                if (!OMNI_SUCCESS.equals(alimTalkAuth.code())) {
                    log.error("알림톡 토큰 발급 실패 : code:[{}], message:[{}]", alimTalkAuth.code(), alimTalkAuth.result());
                    return false;
                }
                redisTemplate.opsForValue().set(SCHEMA_KEY, alimTalkAuth.data().schema());
                redisTemplate.opsForValue().set(TOKEN_KEY, alimTalkAuth.data().token());
                redisTemplate.opsForValue().set(EXPIRED_KEY, alimTalkAuth.data().expired());
            }
        } catch (Exception e) {
            log.error("알림톡 토큰 발급 실패 : {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Redis에서 인증 토큰 가져오기
     */
    private String getAuthToken() {
        String expired = redisTemplate.opsForValue().get(EXPIRED_KEY);
        try {
            if (!StringUtils.hasText(expired) || isTokenExpired(expired)) {
                log.info("토큰이 만료되었거나 없음. 새로운 토큰 요청");
                this.getNewAuth();
            }
        } catch (Exception e) {
            this.getNewAuth();
        }
        return redisTemplate.opsForValue().get(SCHEMA_KEY) + " " + redisTemplate.opsForValue().get(TOKEN_KEY);
    }

    private boolean isTokenExpired(String expired) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date expiredDate = formatter.parse(expired);
            return expiredDate.before(new Date());
        } catch (Exception e) {
            log.error("토큰 만료 체크 중 예외 발생: {}", e.getMessage());
            return true;
        }
    }
}
