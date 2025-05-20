package kr.co.yourplanet.core.alimtalk.support;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkButtonForm;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplateButton;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlimTalkSendFormFactory {

    @Value("${omni.senderkey}")
    private String senderKey;

    public AlimTalkSendForm createFromTemplate(AlimTalkTemplate template, String to, String text) {
        return new AlimTalkSendForm(
            senderKey,
            template.getMsgType(),
            to,
            template.getTemplateCode(),
            text,
            template.getTitle(),
            null,
            createButtonsFromTemplate(template.getButtons()),
            null,
            null
        );
    }

    private List<AlimTalkButtonForm> createButtonsFromTemplate(List<AlimTalkTemplateButton> alimTalkButtons) {
        if (alimTalkButtons == null || alimTalkButtons.isEmpty()) {
            return List.of();
        }

        return alimTalkButtons.stream()
            .map(button -> new AlimTalkButtonForm(
                button.getType(),
                button.getName(),
                button.getUrlPc(),
                button.getUrlMobile(),
                button.getSchemeIos(),
                button.getSchemeAndroid(),
                button.getTarget(),
                button.getChatExtra(),
                button.getChatEvent(),
                button.getBizFormKey(),
                button.getBizFormId()
            ))
            .toList();
    }
}