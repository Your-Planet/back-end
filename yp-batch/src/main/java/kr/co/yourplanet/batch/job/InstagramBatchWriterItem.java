package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.batch.dto.response.InstagramMediaApiForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Mono;

@Getter
@AllArgsConstructor
@Builder
public class InstagramBatchWriterItem {

    private Long memberId;

    private Mono<InstagramMediaApiForm> instagramMediaApiFormMono;
}
