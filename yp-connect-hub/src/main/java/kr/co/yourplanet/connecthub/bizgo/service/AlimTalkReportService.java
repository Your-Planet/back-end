package kr.co.yourplanet.connecthub.bizgo.service;

import kr.co.yourplanet.connecthub.bizgo.dto.AlimTalkReportRequest;
import kr.co.yourplanet.connecthub.bizgo.dto.AlimTalkReportResponse;
import kr.co.yourplanet.connecthub.bizgo.repository.AlimTalkReportRepository;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkReportHistory;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkRequestHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlimTalkReportService {
    private final AlimTalkReportRepository alimTalkReportRepository;

    public AlimTalkReportResponse receiveReport(AlimTalkReportRequest alimTalkReportRequest) {
        AlimTalkReportHistory alimTalkReport = AlimTalkReportHistory.builder()
                .msgKey(alimTalkReportRequest.getMsgKey())
                .reportCode(alimTalkReportRequest.getReportCode())
                .msgType(alimTalkReportRequest.getMsgType())
                .serviceType(alimTalkReportRequest.getServiceType())
                .carrier(alimTalkReportRequest.getCarrier())
                .reportTime(alimTalkReportRequest.getReportTime())
                .ref(alimTalkReportRequest.getRef())
                .sendTime(alimTalkReportRequest.getSendTime())
                .reportText(alimTalkReportRequest.getReportText())
                .build();

        alimTalkReportRepository.save(alimTalkReport);

        return AlimTalkReportResponse.builder()
                .msgKey(alimTalkReportRequest.getMsgKey())
                .build();
    }
}
