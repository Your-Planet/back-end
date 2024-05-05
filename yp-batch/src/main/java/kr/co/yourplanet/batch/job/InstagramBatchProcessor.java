package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.batch.InstagramApiManager;
import kr.co.yourplanet.batch.domain.MemberInstagramInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramBatchProcessor implements ItemProcessor<MemberInstagramInfo, InstagramBatchWriterItem> {

    private final InstagramApiManager instagramApiManager;

    @Override
    public InstagramBatchWriterItem process(MemberInstagramInfo item) throws Exception {
        // WebClient를 사용하여 비동기적으로 데이터 처리하고 응답을 반환
        return InstagramBatchWriterItem.builder()
                .memberId(item.getMemberId())
                .instagramMediaApiFormMono(instagramApiManager.getInstagramUserMedia(item.getInstagramId(), item.getInstagramAccessToken()))
                .build();
    }
}
