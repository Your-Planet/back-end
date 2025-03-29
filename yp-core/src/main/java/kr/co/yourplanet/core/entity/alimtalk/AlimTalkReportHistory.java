package kr.co.yourplanet.core.entity.alimtalk;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkReportHistory extends BasicColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alim_talk_report_history_seq")
    @SequenceGenerator(name = "alim_talk_report_history_seq", sequenceName = "alim_talk_report_history_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;
    @Column(name = "msg_key", nullable = false)
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