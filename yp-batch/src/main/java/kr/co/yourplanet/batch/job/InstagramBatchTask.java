package kr.co.yourplanet.batch.job;

import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class InstagramBatchTask {

    private final JobExecutor jobExecutor;

    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleFileMetadataCleanJob() {
        jobExecutor.executeJob("instagramMediaJob", Map.of());
    }
}
