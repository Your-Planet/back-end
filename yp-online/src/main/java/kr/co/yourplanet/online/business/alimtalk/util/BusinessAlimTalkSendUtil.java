package kr.co.yourplanet.online.business.alimtalk.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JacksonException;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.alimtalk.repository.AlimTalkTemplateRepository;
import kr.co.yourplanet.core.alimtalk.service.AlimTalkSendService;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.MemberType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessAlimTalkSendUtil {

    private final AlimTalkSendService alimTalkSendService;
    private final AlimTalkTemplateRepository alimTalkTemplateRepository;

    @Value("${omni.senderkey}")
    private String senderkey;

    @Async
    public void sendMemberJoinCompleteAlimTalk(Member member) {

        String templateCode = "";

        if (MemberType.CREATOR.equals(member.getMemberType())) {
            templateCode = "YP_ALIM_0002";
        } else if (MemberType.SPONSOR.equals(member.getMemberType())){
            templateCode = "YP_ALIM_0003";
        }

        Optional<AlimTalkTemplate> alimTalkTemplateOptional = alimTalkTemplateRepository.findByTemplateCode(templateCode);

        if(alimTalkTemplateOptional.isPresent()){
            AlimTalkTemplate alimTalkTemplate = alimTalkTemplateOptional.get();

            // 알림톡 문구 변수필드 할당
            String messageText = alimTalkTemplate.getText().replace("#{member.name}", member.getName());

            AlimTalkSendForm alimTalkSendForm = AlimTalkSendForm.ofRequiredFields(alimTalkTemplate, senderkey, member.getTel(), messageText);

            try {
                alimTalkSendService.sendAlimTalk(alimTalkSendForm, member.getId());
            }
            catch (JacksonException e) {
                log.error("회원가입 알림톡 발송 실패 : JacksonException");
            } catch (Exception e){
                log.error("회원가입 알림톡 발송 실패 : {}", e.getMessage());
            }
        }

    }

}
