package kr.co.yourplanet.core.alimtalk.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.alimtalk.repository.AlimTalkTemplateButtonRepository;
import kr.co.yourplanet.core.alimtalk.repository.AlimTalkTemplateRepository;
import kr.co.yourplanet.core.alimtalk.service.AlimTalkTemplateService;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlimTalkTemplateServiceImpl implements AlimTalkTemplateService {

    private final AlimTalkTemplateRepository alimTalkTemplateRepository;
    private final AlimTalkTemplateButtonRepository alimTalkTemplateButtonRepository;

    @Override
    public void saveAlimTalkTemplate(AlimTalkTemplate alimTalkTemplate) {
        alimTalkTemplateRepository.save(alimTalkTemplate);
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
    public List<AlimTalkTemplate> getAlimTalkTemplateAll() {
        return alimTalkTemplateRepository.findAll();
    }
}
