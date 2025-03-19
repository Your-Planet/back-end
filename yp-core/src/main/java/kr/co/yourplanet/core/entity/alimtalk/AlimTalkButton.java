package kr.co.yourplanet.core.entity.alimtalk;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.enums.AlimTalkButtonType;

public class AlimTalkButton {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alim_talk_button_seq")
    @SequenceGenerator(name = "alim_talk_button_seq", sequenceName = "alim_talk_button_seq", allocationSize = 50)
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alim_talk_template_id")
    private AlimTalkTemplate alimTalkTemplate;

    @Enumerated(EnumType.STRING)
    private AlimTalkButtonType type; // 카카오 버튼 종류 (필수)

    @Column(name = "template_code")
    private String name; // 버튼 명 (필수)

    @Column(name = "url_pc")
    private String urlPc; // PC 환경에서 버튼 클릭 시 이동할 URL
    @Column(name = "url_mobile")
    private String urlMobile; // 모바일 환경에서 버튼 클릭 시 이동할 URL
    @Column(name = "scheme_ios")
    private String schemeIos; // iOS 환경에서 버튼 클릭 시 실행할 application custom scheme
    @Column(name = "scheme_android")
    private String schemeAndroid; // Android 환경에서 버튼 클릭 시 실행할 application custom scheme
    @Column(name = "target")
    private String target; // 버튼 type이 WL(웹 링크)일 경우 "target":"out" 입력 시 아웃링크 사용
    @Column(name = "chat_extra")
    private String chatExtra; // 봇/상담톡 전환 시 전달할 메타정보
    @Column(name = "chat_event")
    private String chatEvent; // 봇/상담톡 전환 시 연결할 이벤트 명
    @Column(name = "biz_form_key")
    private String bizFormKey; // 비즈폼 키
    @Column(name = "biz_form_id")
    private String bizFormId; // 비즈폼 ID
}
