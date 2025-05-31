package kr.co.yourplanet.core.alimtalk.dto;

import lombok.Builder;

@Builder
public record AlimTalkSendResponseForm(

    String code,
    String result,
    String msgKey,
    String ref

) {
}
