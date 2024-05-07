package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.core.entity.member.Member;
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

    private final ItemReader<Member> instagramBatchReader;
    private final ItemProcessor<Member, InstagramBatchWriterItem> instagramBatchProcessor;
    private final ItemWriter<InstagramBatchWriterItem> instagramBatchWriter;

    @Bean
    public Job instagramMediaJob() {
        return jobBuilderFactory.get("instagramMediaJob")
                .start(instagramMediaStep())
                .build();
    }

    @Bean
    public Step instagramMediaStep() {
        return stepBuilderFactory.get("instagramMediaStep")
                .<Member, InstagramBatchWriterItem>chunk(5)
                .reader(instagramBatchReader)
                .processor(instagramBatchProcessor)
                .writer(instagramBatchWriter)
                .build();
    }
}
