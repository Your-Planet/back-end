package kr.co.yourplanet.ypbackend.business.instagram.api;

import kr.co.yourplanet.ypbackend.business.instagram.dto.response.InstaUserMedia;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Mono<InstaUserMedia> getInstagramUsername(String accessToken) {
        return webClient.mutate()
                .build()
                .get()
                .uri("https://graph.instagram.com/5753722934752828/media?fields=id,media_type,media_url,permalink,thumbnail_url,username,caption&access_token=IGQWRQVVVUdFg1bkR4TkN6a1pISG0zdnZAndFQzNmphbzJrQWhHYlVTTVl6VE1iaVNobmxJWjFudERlRkpiMTEtZAkhnZA2xuUFVCTE5xcXV0VVlJSjBpc216UlhOd3hxVTNmT1QwUTN1b3RhZAwZDZD")
                .retrieve()
                .bodyToMono(InstaUserMedia.class)
                .onErrorResume(throwable -> {
                    return Mono.error(new RuntimeException(throwable));
                });
    }

    public Mono<String> getNaver(String accessToken) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://naver.com")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        return webClient.mutate()
                .baseUrl("https://naver.com")
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
