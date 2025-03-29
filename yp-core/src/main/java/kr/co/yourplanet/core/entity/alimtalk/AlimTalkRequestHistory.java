package kr.co.yourplanet.core.entity.alimtalk;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
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
public class AlimTalkRequestHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alim_talk_request_history_seq")
    @SequenceGenerator(name = "alim_talk_request_history_seq", sequenceName = "alim_talk_request_history_seq", allocationSize = 50)
    @Column(name = "id")
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "to_phone_number")
    private String toPhoneNumber;
    @Column(name = "template_code")
    private String templateCode;
    @Column(name = "send_text")
    private String sendText;
    @Column(name = "msg_key")
    private String msgKey;
    @Column(name = "send_request_result_code")
    private String sendRequestResultCode;
    @Column(name = "send_request_result_message")
    private String sendRequestResultMessage;
}

