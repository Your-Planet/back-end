package kr.co.yourplanet.core.entity.alimtalk;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkReportHistory {
    @Id
    private Long id;
    @Column(name = "msg_key")
    private String msgKey;
    @Column(name = "report_code")
    private String reportCode;
    @Column(name = "msg_type")
    private String msgType;
    @Column(name = "service_type")
    private String serviceType;
    @Column(name = "carrier")
    private String carrier;
    @Column(name = "report_time")
    private LocalDateTime reportTime;
    @Column(name = "ref")
    private String ref;
    @Column(name = "send_time")
    private LocalDateTime sendTime;
    @Column(name = "report_text")
    private String reportText;
}