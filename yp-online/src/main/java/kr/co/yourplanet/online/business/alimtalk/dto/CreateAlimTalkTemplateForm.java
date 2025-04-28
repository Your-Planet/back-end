package kr.co.yourplanet.online.business.alimtalk.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.yourplanet.core.enums.AlimTalkButtonType;

public record CreateAlimTalkTemplateForm(
    String templateCode,
    String title,
    String text,
    List<CreateAlimTalkTemplateButtonForm> buttons
) {
    public record CreateAlimTalkTemplateButtonForm(
        AlimTalkButtonType type,
        String name,
        String urlPc,
        @Schema(description = "알림톡버튼 type = 웹링크(WL)일 경우 필수")
        String urlMobile,
        String schemeIos,
        @Schema(description = "알림톡버튼 type = 앱링크(AL)일 경우 필수")
        String schemeAndroid,
        String target,
        String chatExtra,
        String chatEvent,
        String bizFormKey,
        String bizFormId

    ) {

    }
}
