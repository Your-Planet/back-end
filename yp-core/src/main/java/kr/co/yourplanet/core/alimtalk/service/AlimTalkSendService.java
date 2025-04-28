package kr.co.yourplanet.core.alimtalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendResponseForm;

public interface AlimTalkSendService {

    AlimTalkSendResponseForm sendAlimTalk(AlimTalkSendForm alimTalkSendForm, Long memberId) throws JsonProcessingException;
}
