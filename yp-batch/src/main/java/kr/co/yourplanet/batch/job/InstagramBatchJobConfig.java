package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.core.entity.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class InstagramBatchJobConfig {

    private final JobRepository jobRepository; // JobRepository를 주입받아 사용
    private final PlatformTransactionManager transactionManager; // StepBuilder에 필요한 트랜잭션 매니저

    private final ItemReader<Member> instagramBatchReader;
    private final ItemProcessor<Member, InstagramBatchWriterItem> instagramBatchProcessor;
    private final ItemWriter<InstagramBatchWriterItem> instagramBatchWriter;

    @Bean
    public Job instagramMediaJob() {
        return new JobBuilder("instagramMediaJob", jobRepository)
                .start(instagramMediaStep())
                .build();
    }

    @Bean
    public Step instagramMediaStep() {
        return new StepBuilder("instagramMediaStep", jobRepository)
                .<Member, InstagramBatchWriterItem>chunk(5, transactionManager) // 트랜잭션 매니저 추가
                .reader(instagramBatchReader)
                .processor(instagramBatchProcessor)
                .writer(instagramBatchWriter)
                .build();
    }
}