package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.batch.domain.MemberInstagramInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class InstagramBatchJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ItemReader<MemberInstagramInfo> instagramBatchReader;
    private final ItemProcessor<MemberInstagramInfo, InstagramBatchWriterItem> instagramBatchProcessor;
    private final ItemWriter<InstagramBatchWriterItem> instagramBatchWriter;

    @Bean
    public Job instaSyncJob() {
        return jobBuilderFactory.get("instaSyncJob")
                .start(mediaStep())
                .build();
    }

    @Bean
    public Step mediaStep() {
        return stepBuilderFactory.get("mediaStep")
                .<MemberInstagramInfo, InstagramBatchWriterItem>chunk(5)
                .reader(instagramBatchReader)
                .processor(instagramBatchProcessor)
                .writer(instagramBatchWriter)
                .build();
    }
}
