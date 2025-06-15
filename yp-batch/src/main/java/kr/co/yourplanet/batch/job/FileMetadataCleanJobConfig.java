package kr.co.yourplanet.batch.job;

import kr.co.yourplanet.core.entity.file.FileMetadata;
import kr.co.yourplanet.batch.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class FileMetadataCleanJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final FileMetadataRepository fileMetadataRepository;

    @Bean
    public Job deleteOldUnuploadedFilesJob() {
        return new JobBuilder("deleteOldUnuploadedFilesJob", jobRepository)
                .start(deleteOldUnuploadedFilesStep())
                .build();
    }

    @Bean
    public Step deleteOldUnuploadedFilesStep() {
        return new StepBuilder("deleteOldUnuploadedFilesStep", jobRepository)
                .<FileMetadata, FileMetadata>chunk(100, transactionManager)
                .reader(fileMetadataReader())
                .writer(fileMetadataWriter())
                .build();
    }

    @Bean
    public ItemReader<FileMetadata> fileMetadataReader() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(1);
        List<FileMetadata> oldFiles = fileMetadataRepository.findByUploadedFalseAndCreateDateBefore(cutoff);
        return new ListItemReader<>(oldFiles);
    }

    @Bean
    public ItemWriter<FileMetadata> fileMetadataWriter() {
        return items ->
                fileMetadataRepository.deleteAllInBatch(new ArrayList<>(items.getItems()));
    }
}