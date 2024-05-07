package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.core.entity.instagram.InstagramMedia;
import kr.co.yourplanet.batch.repository.InstagramMediaRepository;
import kr.co.yourplanet.batch.util.InstagramMediaConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InstagramBatchWriter implements ItemWriter<InstagramBatchWriterItem> {

    private final InstagramMediaRepository instagramMediaRepository;
    private final InstagramMediaConverter instagramMediaConverter;

    @Override
    public void write(List<? extends InstagramBatchWriterItem> items) throws Exception {
        // ItemWriter에서는 WebClient의 응답을 처리
        for (InstagramBatchWriterItem item : items) {
            // WebClient의 응답을 기다렸다가 처리
            item.getInstagramMediaApiFormMono().subscribe(response -> {
                if (!CollectionUtils.isEmpty(response.getMediaList())) {
                    List<InstagramMedia> instagramMediaList = instagramMediaConverter.convertToInstagramMediaList(response.getMediaList(), item.getMemberId());
                    instagramMediaRepository.saveAll(instagramMediaList);
                }
            });
        }
    }
}
