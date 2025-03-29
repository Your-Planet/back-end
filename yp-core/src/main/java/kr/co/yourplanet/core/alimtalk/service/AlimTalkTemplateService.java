package kr.co.yourplanet.core.alimtalk.service;

import java.util.List;
import java.util.Optional;

import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;

public interface AlimTalkTemplateService {

    void saveAlimTalkTemplate(AlimTalkTemplate alimTalkTemplate);

    Optional<AlimTalkTemplate> getAlimTalkTemplateById(Long id);

    Optional<AlimTalkTemplate> getAlimTalkTemplateByTemplateCode(String templateCode);

    List<AlimTalkTemplate> getAlimTalkTemplateAll();
}
