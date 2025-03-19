package kr.co.yourplanet.core.alimtalk.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkAuth;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.alimtalk.service.AlimTalkApiService;
import kr.co.yourplanet.core.repository.AlimTalkRequestRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
@RequiredArgsConstructor
public class AlimTalkApiServiceImpl implements AlimTalkApiService {
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final RedisTemplate<String, String> redisTemplate;
    private final AlimTalkRequestRepository alimTalkRequestRepository;

    @Override
    public boolean getNewAuth() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(this.host+this.authUrl)
                .method("POST", null)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-IB-Client-Id", this.id)
                .addHeader("X-IB-Client-Passwd", this.password)
                .build();
        try {
            Response response = client.newCall(request).execute();
            assert response.body() != null;
            AlimTalkAuth alimTalkAuth = objectMapper.readValue(response.body().string(), AlimTalkAuth.class);

            // ToDo : API 응답결과(AlimTalkAuth.code) 확인하는 로직 추가

            redisTemplate.opsForValue().set("schema", alimTalkAuth.getData().getSchema());
            redisTemplate.opsForValue().set("token", alimTalkAuth.getData().getToken());
            redisTemplate.opsForValue().set("expired", alimTalkAuth.getData().getExpired());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // ToDo : 상세 전송 내역에 대한 논의 필요
    // ToDo : String content -> String 타입이 아닌 DTO 객체로 처리하자
    @Override
    public boolean sendAlimTalk(AlimTalkSendForm alimTalkSendForm, Long memberId) throws JsonProcessingException {

        String authToken = getAuthToken();
        String json = objectMapper.writeValueAsString(alimTalkSendForm);

        // 알림톡 전송
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(this.host + this.sendUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", authToken)
                .build();

        // ToDo : 알림톡 발송내역 DB저장을 여기서 수행할 것인지 or 해당 메소드를 호출한 호출자에서 처리할 것인지
        try (Response response = client.newCall(request).execute()) {
            // ToDo : 알림톡 요청 히스토리 저장

            if (response.code() != 200) {
                // ToDo : 알림톡 요청 히스토리 업데이트
            } else {
                // ToDo : 알림톡 요청 히스토리 업데이트
            }
        } catch (Exception e) {
            return false;
        }
        return  true;
    }

    /*
     * redis에서 token 생성에 필요한 정보를 읽어 온다.
     */
    private String getAuthToken() {
        String expired = redisTemplate.opsForValue().get("expired");

        try {
            if (!StringUtils.hasText(expired)) {
                this.getNewAuth();
            }

            if (!StringUtils.hasText(redisTemplate.opsForValue().get("schema"))) {
                this.getNewAuth();
            }

            if (!StringUtils.hasText(redisTemplate.opsForValue().get("token"))) {
                this.getNewAuth();
            }

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date expiredDate = formatter.parse(expired);
            // 만료 시간이 현재 시간 이후라면 새로운 토큰을 발급 받아야한다.
            if (expiredDate.after(new Date())) {
                this.getNewAuth();
            }
        }catch (Exception e) {
            this.getNewAuth();
        }

        return redisTemplate.opsForValue().get("schema") + " " + redisTemplate.opsForValue().get("token");
    }

}
