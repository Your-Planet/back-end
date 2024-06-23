package kr.co.yourplanet.batch;

import kr.co.yourplanet.batch.dto.response.InstagramMediaApiForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
@Slf4j
public class InstagramApiManager {

    private final WebClient webClient;

    @Value("${instagram.api-url}")
    private String instagramUrl;

    private static final int INSTAGRAM_MEDIA_LIMIT = 100;

    public Mono<InstagramMediaApiForm> getInstagramUserMedia(String instagramId, String instagramAccessToken) {
        String mediaFields = "id,media_type,media_url,permalink,thumbnail_url,username,caption";
        URI uri = UriComponentsBuilder.fromUriString(instagramUrl)
                .path("/{id}/media")
                .queryParam("access_token", instagramAccessToken)
                .queryParam("fields", mediaFields)
                .queryParam("limit", INSTAGRAM_MEDIA_LIMIT)
                .buildAndExpand(instagramId)
                .encode(StandardCharsets.UTF_8)
                .toUri();

        return webClient.mutate()
                .build()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(InstagramMediaApiForm.class)
                .onErrorResume(e -> {
                    log.error(instagramId + " : Instagram Media API call failed : " + e.getMessage());
                    return Mono.just(new InstagramMediaApiForm());
                });
    }

    public Mono<InstagramMediaApiForm> getInstagramUserMediaNext(String nextUrl) {
        return webClient.mutate()
                .build()
                .get()
                .uri(nextUrl)
                .retrieve()
                .bodyToMono(InstagramMediaApiForm.class)
                .onErrorResume(e -> {
                    log.error("Instagram Media API next URL call failed : " + e.getMessage());
                    return Mono.just(new InstagramMediaApiForm());
                });
    }

}
