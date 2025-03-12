package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.batch.InstagramApiManager;
import kr.co.yourplanet.batch.dto.response.InstagramMediaApiForm;
import kr.co.yourplanet.batch.repository.InstagramMediaRepository;
import kr.co.yourplanet.batch.util.InstagramMediaConverter;
import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstagramBatchWriter implements ItemWriter<InstagramBatchWriterItem> {

    private final InstagramMediaRepository instagramMediaRepository;
    private final InstagramMediaConverter instagramMediaConverter;
    private final InstagramApiManager instagramApiManager;

    @Override
    public void write(Chunk<? extends InstagramBatchWriterItem> items) throws Exception {
        // ItemWriter에서는 WebClient의 응답을 처리
        for (InstagramBatchWriterItem item : items) {
            // WebClient의 응답을 기다렸다가 처리
            item.getInstagramMediaApiFormMono().subscribe(response -> getAllInstagramMedias(response, item.getMemberId()));
        }
    }

    private void getAllInstagramMedias(InstagramMediaApiForm instagramMediaApiForm, Long memberId) {
        if (!CollectionUtils.isEmpty(instagramMediaApiForm.getMediaList())) {
            List<InstagramMedia> instagramMediaList = instagramMediaConverter.convertToInstagramMediaList(instagramMediaApiForm.getMediaList(), memberId);
            instagramMediaRepository.saveAll(instagramMediaList);
        }
        // 인스타그램 미디어 조회 API paging 처리를 위한 재귀함수
        if (instagramMediaApiForm.getPaging() != null && StringUtils.hasText(instagramMediaApiForm.getPaging().getNext())) {
            Mono<InstagramMediaApiForm> test = instagramApiManager.getInstagramUserMediaNext(instagramMediaApiForm.getPaging().getNext());

            test.subscribe(response -> getAllInstagramMedias(response, memberId));
        }
    }
}
