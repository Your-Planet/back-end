package kr.co.yourplanet.core.alimtalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;

public interface AlimTalkApiService {

    boolean getNewAuth();

    boolean sendAlimTalk(AlimTalkSendForm alimTalkSendForm, Long memberId) throws JsonProcessingException;
}
