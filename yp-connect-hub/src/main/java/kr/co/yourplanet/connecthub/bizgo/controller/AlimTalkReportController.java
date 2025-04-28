package kr.co.yourplanet.connecthub.bizgo.controller;

import kr.co.yourplanet.connecthub.bizgo.dto.AlimTalkReportRequest;
import kr.co.yourplanet.connecthub.bizgo.dto.AlimTalkReportResponse;
import kr.co.yourplanet.connecthub.bizgo.service.AlimTalkReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlimTalkReportController {
    private final AlimTalkReportService alimTalkReportService;

    @PostMapping("/alimtalk-report")
    public AlimTalkReportResponse receiveReport(@RequestBody AlimTalkReportRequest alimTalkReportRequest) {
        return alimTalkReportService.receiveReport(alimTalkReportRequest);
    }
}
