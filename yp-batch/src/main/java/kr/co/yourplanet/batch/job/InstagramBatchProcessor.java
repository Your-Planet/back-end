package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.batch.InstagramApiManager;
import kr.co.yourplanet.core.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramBatchProcessor implements ItemProcessor<Member, InstagramBatchWriterItem> {

    private final InstagramApiManager instagramApiManager;

    @Override
    public InstagramBatchWriterItem process(Member itemMember) throws Exception {
        // WebClient를 사용하여 비동기적으로 데이터 처리하고 응답을 반환
        return InstagramBatchWriterItem.builder()
                .memberId(itemMember.getId())
                .instagramMediaApiFormMono(instagramApiManager.getInstagramUserMedia(itemMember.getInstagramId(), itemMember.getInstagramAccessToken()))
                .build();
    }
}