package kr.co.yourplanet.online.business.alimtalk.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.enums.AlimTalkButtonType;
import kr.co.yourplanet.core.enums.AlimTalkMsgType;

public record AlimTalkTemplateForm(
    @NotBlank(message = "템플릿 코드는 필수입니다")
    @Schema(description = "템플릿 코드", example = "YP_ALIM_0000")
    String templateCode,
    @NotBlank(message = "템플릿명은 필수입니다")
    @Schema(description = "템플릿명", example = "가입환영 알림")
    String name,
    @NotBlank(message = "메시지 내용은 필수입니다")
    @Schema(description = "메시지 내용", example = "환영합니다 {member.name}님.")
    String text,
    @NotNull(message = "메시지 타입은 필수입니다.")
    @Schema(description = "메시지 타입", example = "AT")
    AlimTalkMsgType msgType,
    @Schema(description = "알림톡 버튼(존재하는 경우만)")
    List<CreateAlimTalkTemplateButtonForm> buttons
) {
    public record CreateAlimTalkTemplateButtonForm(
        @Schema(description = "알림톡 버튼 타입 (버튼 존재시 필수)", example = "WL")
        AlimTalkButtonType type,
        @Schema(description = "버튼명 (버튼 존재시 필수)")
        String name,
        String urlPc,
        @Schema(description = "모바일 웹링크 [알림톡 버튼 타입 = 웹링크(WL)일 경우 필수]")
        String urlMobile,
        String schemeIos,
        @Schema(description = "알림톡 버튼 타입 = 앱링크(AL)일 경우 필수")
        String schemeAndroid,
        String target,
        String chatExtra,
        String chatEvent,
        String bizFormKey,
        String bizFormId

    ) {

    }
}
