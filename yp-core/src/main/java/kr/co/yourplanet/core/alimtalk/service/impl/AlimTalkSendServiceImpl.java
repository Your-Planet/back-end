package kr.co.yourplanet.core.alimtalk.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.yourplanet.core.alimtalk.adapter.AlimTalkApiClient;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendResponseForm;
import kr.co.yourplanet.core.alimtalk.repository.AlimTalkRequestRepository;
import kr.co.yourplanet.core.alimtalk.service.AlimTalkSendService;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkRequestHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlimTalkSendServiceImpl implements AlimTalkSendService {
    private final AlimTalkApiClient alimTalkApiClient;
    private final AlimTalkRequestRepository alimTalkRequestRepository;

    /**
     * ✅ API 호출 후 DB 저장하는 비즈니스 로직
     */
    @Transactional
    @Override
    public void sendAlimTalk(AlimTalkSendForm alimTalkSendForm, Long memberId) throws JsonProcessingException {

        // ToDo : AlimTalkSendForm 객체에 대한 validation

        AlimTalkSendResponseForm result = alimTalkApiClient.sendAlimTalk(alimTalkSendForm);

        AlimTalkRequestHistory history = AlimTalkRequestHistory.builder()
            .memberId(memberId)
            .toPhoneNumber(alimTalkSendForm.to())
            .templateCode(alimTalkSendForm.templateCode())
            .sendText(alimTalkSendForm.text())
            .msgKey(result.msgKey())
            .sendRequestResultCode(result.code())
            .sendRequestResultMessage(result.result())
            .build();

        alimTalkRequestRepository.save(history);
    }

}