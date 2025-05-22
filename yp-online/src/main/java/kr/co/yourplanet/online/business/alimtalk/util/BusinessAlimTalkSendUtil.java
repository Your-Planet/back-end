package kr.co.yourplanet.online.business.alimtalk.util;

import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JacksonException;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.alimtalk.repository.AlimTalkTemplateRepository;
import kr.co.yourplanet.core.alimtalk.service.AlimTalkSendService;
import kr.co.yourplanet.core.alimtalk.support.AlimTalkSendFormFactory;
import kr.co.yourplanet.core.alimtalk.support.AlimTalkVariableResolver;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.AlimTalkTemplateCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessAlimTalkSendUtil {

    private final AlimTalkSendService alimTalkSendService;
    private final AlimTalkTemplateRepository alimTalkTemplateRepository;
    private final AlimTalkSendFormFactory alimTalkSendFormFactory;

    private static final String ALIM_TALK_SEND_REQUEST_SUCCESS = "A000";

    @Async
    /*
     * 회원가입 완료 알림톡 발송
     */
    public void sendMemberJoinCompleteAlimTalk(Member member) {

        // 1. 템플릿코드 지정
        String templateCode = switch (member.getMemberType()) {
            case CREATOR -> AlimTalkTemplateCode.MEMBER_JOIN_CREATOR.getCode();
            case SPONSOR -> AlimTalkTemplateCode.MEMBER_JOIN_SPONSOR.getCode();
            default -> "";
        };

        // 2. 템플릿코드로 알림톡 템플릿 조회
        Optional<AlimTalkTemplate> alimTalkTemplateOptional = alimTalkTemplateRepository.findByTemplateCode(templateCode);

        if(alimTalkTemplateOptional.isPresent()){
            AlimTalkTemplate alimTalkTemplate = alimTalkTemplateOptional.get();

            // 3. 알림톡 문구 변수치환
            Map<String, Object> contextObjectMap = Map.of("member", member);
            String messageText = AlimTalkVariableResolver.resolve(alimTalkTemplate.getText(), contextObjectMap);
            AlimTalkSendForm alimTalkSendForm = alimTalkSendFormFactory.createFromTemplate(alimTalkTemplate, member.getTel(), messageText);

            try {
                // 4. 알림톡 발송요청
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
