package kr.co.yourplanet.batch.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import kr.co.yourplanet.core.alimtalk.service.AlimTalkApiService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlimTalkTask {
    private final AlimTalkApiService alimTalk;

    @PostConstruct
    public void onStartup() throws Exception {
        // 애플리케이션 시작 시 즉시 실행
        runAlimTalkAuthJob();
    }
    @Scheduled(cron = "0 0 */12 * * *")
    public void scheduledTask() throws Exception {
        runAlimTalkAuthJob();
    }

    public void runAlimTalkAuthJob() throws Exception {
        alimTalk.getNewAuth();
    }
}
