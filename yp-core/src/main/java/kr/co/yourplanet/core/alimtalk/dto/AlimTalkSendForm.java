package kr.co.yourplanet.core.alimtalk.dto;

import java.util.List;

import kr.co.yourplanet.core.enums.AlimTalkMsgType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlimTalkSendForm {

    private String senderKey; // 카카오 비즈메시지 발신 프로필 키
    private AlimTalkMsgType msgType; // 카카오 알림톡 메시지 타입
    private String to; // 수신번호 (01012345678 형식)
    private String templateCode; // 알림톡 템플릿 코드
    private String text; // 알림톡 내용
    private String title; // 알림톡 제목 (강조표기형 템플릿)
    private String header; // 메시지 상단에 표시할 제목
    private List<AlimTalkButtonForm> button; // 카카오 버튼 정보 (최대 5개)
    private String ref; // 참조 필드
    // ToDo : fallback 구현
    private Object fallback; // 실패 시 전송될 Fallback 메시지 정보

}
