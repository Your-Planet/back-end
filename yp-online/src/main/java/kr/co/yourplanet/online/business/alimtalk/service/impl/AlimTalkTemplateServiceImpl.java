package kr.co.yourplanet.online.business.alimtalk.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kr.co.yourplanet.core.alimtalk.repository.AlimTalkTemplateButtonRepository;
import kr.co.yourplanet.core.alimtalk.repository.AlimTalkTemplateRepository;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplateButton;
import kr.co.yourplanet.online.business.alimtalk.dto.AlimTalkTemplateForm;
import kr.co.yourplanet.online.business.alimtalk.service.AlimTalkTemplateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlimTalkTemplateServiceImpl implements AlimTalkTemplateService {

    private final AlimTalkTemplateRepository alimTalkTemplateRepository;
    private final AlimTalkTemplateButtonRepository alimTalkTemplateButtonRepository;

    @Override
    @Transactional
    public void mergeAlimTalkTemplate(AlimTalkTemplateForm alimTalkTemplateForm) {

        Optional<AlimTalkTemplate> optionalTemplate = alimTalkTemplateRepository.findByTemplateCode(
            alimTalkTemplateForm.templateCode());

        AlimTalkTemplate template = optionalTemplate.orElseGet(() -> AlimTalkTemplate.builder()
            .templateCode(alimTalkTemplateForm.templateCode())
            .templateName(alimTalkTemplateForm.name())
            .text(alimTalkTemplateForm.text())
            .msgType(alimTalkTemplateForm.msgType())
            .build());

        template.updateTemplate(
            alimTalkTemplateForm.name(),
            alimTalkTemplateForm.text(),
            alimTalkTemplateForm.msgType()
        );

        alimTalkTemplateRepository.save(template);

        // 버튼 삭제 후 재등록 방식
        alimTalkTemplateButtonRepository.deleteAllByAlimTalkTemplate(template);

        if (!CollectionUtils.isEmpty(alimTalkTemplateForm.buttons())) {
            for (AlimTalkTemplateForm.CreateAlimTalkTemplateButtonForm buttonForm : alimTalkTemplateForm.buttons()) {
                AlimTalkTemplateButton button = AlimTalkTemplateButton.builder()
                    .type(buttonForm.type())
                    .name(buttonForm.name())
                    .urlPc(buttonForm.urlPc())
                    .urlMobile(buttonForm.urlMobile())
                    .schemeIos(buttonForm.schemeIos())
                    .schemeAndroid(buttonForm.schemeAndroid())
                    .target(buttonForm.target())
                    .chatExtra(buttonForm.chatExtra())
                    .chatEvent(buttonForm.chatEvent())
                    .bizFormKey(buttonForm.bizFormKey())
                    .bizFormId(buttonForm.bizFormId())
                    .alimTalkTemplate(template)
                    .build();

                alimTalkTemplateButtonRepository.save(button);

            }
        }

    }

    @Override
    public Optional<AlimTalkTemplate> getAlimTalkTemplateById(Long id) {
        return alimTalkTemplateRepository.findById(id);
    }

    @Override
    public Optional<AlimTalkTemplate> getAlimTalkTemplateByTemplateCode(String templateCode) {
        return alimTalkTemplateRepository.findByTemplateCode(templateCode);
    }

    @Override
    public List<AlimTalkTemplateForm> getAllAlimTalkTemplates() {
        List<AlimTalkTemplateForm> returnList = new ArrayList<>();

        List<AlimTalkTemplate> alimTalkTemplateList = alimTalkTemplateRepository.findAll();

        if (CollectionUtils.isEmpty(alimTalkTemplateList)) {
            return List.of();
        }

        for (AlimTalkTemplate template : alimTalkTemplateList) {

            List<AlimTalkTemplateForm.CreateAlimTalkTemplateButtonForm> buttonForms =
                Optional.ofNullable(template.getButtons()).orElse(List.of()).stream()
                    .map(button -> new AlimTalkTemplateForm.CreateAlimTalkTemplateButtonForm(
                        button.getType(), button.getName(), button.getUrlPc(), button.getUrlMobile(),
                        button.getSchemeIos(), button.getSchemeAndroid(), button.getTarget(),
                        button.getChatExtra(), button.getChatEvent(), button.getBizFormKey(), button.getBizFormId()
                    ))
                    .toList();

            returnList.add(new AlimTalkTemplateForm(
                template.getTemplateCode(),
                template.getTemplateName(),
                template.getText(),
                template.getMsgType(),
                buttonForms)
            );

        }

        return returnList;
    }
}
