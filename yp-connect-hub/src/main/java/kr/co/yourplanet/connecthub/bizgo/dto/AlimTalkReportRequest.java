package kr.co.yourplanet.connecthub.bizgo.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkReportRequest {
    private String msgKey;
    private String reportCode;
    private String msgType;
    private String serviceType;
    private String carrier;
    private LocalDateTime reportTime;
    private String ref;
    private LocalDateTime sendTime;
    private String reportText;
}
