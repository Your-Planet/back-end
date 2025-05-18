package kr.co.yourplanet.online.business.alimtalk.service;

import java.util.List;
import java.util.Optional;

import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;
import kr.co.yourplanet.online.business.alimtalk.dto.AlimTalkTemplateForm;

public interface AlimTalkTemplateService {

    void mergeAlimTalkTemplate(AlimTalkTemplateForm alimTalkTemplateForm);

    Optional<AlimTalkTemplate> getAlimTalkTemplateById(Long id);

    Optional<AlimTalkTemplate> getAlimTalkTemplateByTemplateCode(String templateCode);

    List<AlimTalkTemplateForm> getAllAlimTalkTemplates();
}
