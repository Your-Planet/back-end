package kr.co.yourplanet.core.entity.alimtalk;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.entity.BasicColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkRequestHistory extends BasicColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alim_talk_request_history_seq")
    @SequenceGenerator(name = "alim_talk_request_history_seq", sequenceName = "alim_talk_request_history_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;
    @Column(name = "template_code", nullable = false)
    private String templateCode;
    @Column(name = "to_phone_number", nullable = false)
    private String toPhoneNumber;
    @Column(name = "send_text")
    private String sendText;
    @Column(name = "msg_key")
    private String msgKey;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "send_request_result_code")
    private String sendRequestResultCode;
    @Column(name = "send_request_result_message")
    private String sendRequestResultMessage;
}

