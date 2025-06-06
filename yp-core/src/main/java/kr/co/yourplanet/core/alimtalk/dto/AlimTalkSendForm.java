package kr.co.yourplanet.core.alimtalk.dto;

import java.util.List;

import kr.co.yourplanet.core.enums.AlimTalkMsgType;

public record AlimTalkSendForm(
    String senderKey, // 카카오 비즈메시지 발신 프로필 키
    AlimTalkMsgType msgType, // 카카오 알림톡 메시지 타입
    String to, // 수신번호 (01012345678 형식)
    String templateCode, // 알림톡 템플릿 코드
    String text, // 알림톡 내용
    String title, // 알림톡 제목 (강조표기형 템플릿)
    String header, // 메시지 상단에 표시할 제목
    List<AlimTalkButtonForm> button, // 카카오 버튼 정보 (최대 5개)
    String ref, // 참조 필드
    Object fallback // 실패 시 전송될 Fallback 메시지 정보
) {

}