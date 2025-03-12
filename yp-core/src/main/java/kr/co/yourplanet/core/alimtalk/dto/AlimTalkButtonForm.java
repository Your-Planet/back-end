package kr.co.yourplanet.core.alimtalk.dto;

import kr.co.yourplanet.core.enums.AlimTalkButtonType;
import lombok.Data;

@Data
public class AlimTalkButtonForm {
    private AlimTalkButtonType type; // 카카오 버튼 종류 (필수)
    private String name; // 버튼 명 (필수)
    private String urlPc; // PC 환경에서 버튼 클릭 시 이동할 URL
    private String urlMobile; // 모바일 환경에서 버튼 클릭 시 이동할 URL
    private String schemeIos; // iOS 환경에서 버튼 클릭 시 실행할 application custom scheme
    private String schemeAndroid; // Android 환경에서 버튼 클릭 시 실행할 application custom scheme
    private String target; // 버튼 type이 WL(웹 링크)일 경우 "target":"out" 입력 시 아웃링크 사용
    private String chatExtra; // 봇/상담톡 전환 시 전달할 메타정보
    private String chatEvent; // 봇/상담톡 전환 시 연결할 이벤트 명
    private String bizFormKey; // 비즈폼 키
    private String bizFormId; // 비즈폼 ID
}
