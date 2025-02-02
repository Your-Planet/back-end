package kr.co.yourplanet.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkRequestHistory;
import kr.co.yourplanet.core.repository.AlimTalkRequestRepository;
import kr.co.yourplanet.core.util.AlimTalkAuth;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class AlimTalkService {
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

    private final RedisTemplate<String, String> redisTemplate;
    private final AlimTalkRequestRepository alimTalkRequestRepository;

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
            ObjectMapper objectMapper = new ObjectMapper();
            assert response.body() != null;
            AlimTalkAuth alimTalkAuth = objectMapper.readValue(response.body().string(), AlimTalkAuth.class);

            redisTemplate.opsForValue().set("schema", alimTalkAuth.getData().getSchema());
            redisTemplate.opsForValue().set("token", alimTalkAuth.getData().getToken());
            redisTemplate.opsForValue().set("expired", alimTalkAuth.getData().getExpired());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // ToDo : 상세 전송 내역에 대한 논의 필요
    // 메세지유형을 저장하고 유형에 맞는 텍스트를 생성해 전송하도록 수정 필요
    public boolean sendAlimTalk(String content, Long memberId) {
        // redis에서 token 생성에 필요한 정보를 읽어 온다.
        String authToken = "";
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

        authToken = authToken + redisTemplate.opsForValue().get("schema") + " ";
        authToken = authToken + redisTemplate.opsForValue().get("token");

        // 알림톡 전송
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(content, mediaType);
        Request request = new Request.Builder()
                .url(this.host + this.sendUrl)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", authToken)
                .build();

        try {
            // ToDo : 알림톡 요청 히스토리 저장
            Response response = client.newCall(request).execute();
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

}
