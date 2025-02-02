package kr.co.yourplanet.core.entity.alimtalk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkRequestHistory {
    @Id
    private Long id;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "msg_type")
    private String msgType;
    @Column(name = "template_code")
    private String templateCode;
    @Column(name = "send_text")
    private String sendText;
    @Column(name = "msg_key")
    private String msgKey;
    @Column(name = "request_success")
    private boolean requestSuccess;
}

