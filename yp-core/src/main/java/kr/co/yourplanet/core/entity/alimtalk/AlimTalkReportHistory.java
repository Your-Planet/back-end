package kr.co.yourplanet.core.entity.alimtalk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

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