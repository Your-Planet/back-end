package kr.co.yourplanet.batch.job;

import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobExecutor {

    private final JobLauncher jobLauncher;
    private final JobLocator jobLocator;

    public void executeJob(String jobName, Map<String, Object> params) {
        log.info("Starting the batch job: {}", jobName);
        try {
            var job = jobLocator.getJob(jobName);
            var builder = new JobParametersBuilder();
            params.forEach((key, value) -> builder.addString(key, value.toString()));

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("jobID", String.valueOf(System.currentTimeMillis()))
                    .toJobParameters();

            final JobExecution execution = jobLauncher.run(job, jobParameters);
            log.info("Job {} executed with status: {}", jobName, execution.getStatus());
        } catch (final Exception e) {
            log.error("Failed to execute job {}: {}", jobName, e.getMessage(), e);
        }
    }
}
