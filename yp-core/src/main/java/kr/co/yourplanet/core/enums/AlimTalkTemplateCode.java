package kr.co.yourplanet.core.enums;

import lombok.Getter;

@Getter
public enum AlimTalkTemplateCode {

    AUTH_CODE("YP_ALIM_0001", "인증번호"),
    MEMBER_JOIN_CREATOR("YP_ALIM_0002", "회원가입 완료 작가"),
    MEMBER_JOIN_SPONSOR("YP_ALIM_0003", "회원가입 완료 광고주"),
    PROJECT_NEGOTIATE_COMMON("YP_ALIM_0005", "신규 협상 접수 알림(공통)"),
    PROJECT_ACCEPT_CREATOR("YP_ALIM_0006", "의뢰 수락 알림(작가)"),
    PROJECT_ACCEPT_SPONSOR("YP_ALIM_0007", "의뢰 수락 알림(브랜드 파트너)"),
    PROJECT_REJECT_SPONSOR("YP_ALIM_0014", "프로젝트 거절 알림(브랜드 파트너)"),
    PROJECT_CANCEL_CREATOR("YP_ALIM_0015", "프로젝트 취소 알림(작가)"),
    PROJECT_SUBMISSION_SENT_SPONSOR("YP_ALIM_0011", "작업물 도착 알림(브랜드 파트너)"),
    PROJECT_SUBMISSION_MODIFICATION_REQUEST_CREATOR("YP_ALIM_0010", "신규 수정 요청 접수(작가)"),
    PROJECT_SUBMISSION_CONFIRMED_CREATOR("YP_ALIM_0012", "최종 작업물 승인 알림(작가)");

    private final String code;
    private final String description;

    AlimTalkTemplateCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
