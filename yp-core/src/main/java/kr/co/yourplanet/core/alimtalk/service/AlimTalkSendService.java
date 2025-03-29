package kr.co.yourplanet.core.alimtalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;

public interface AlimTalkSendService {

    void sendAlimTalk(AlimTalkSendForm alimTalkSendForm, Long memberId) throws JsonProcessingException;
}
