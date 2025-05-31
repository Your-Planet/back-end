package kr.co.yourplanet.online.business.alimtalk.util;

import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JacksonException;

import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendForm;
import kr.co.yourplanet.core.alimtalk.dto.AlimTalkSendResponseForm;
import kr.co.yourplanet.core.alimtalk.repository.AlimTalkTemplateRepository;
import kr.co.yourplanet.core.alimtalk.service.AlimTalkSendService;
import kr.co.yourplanet.core.alimtalk.support.AlimTalkSendFormFactory;
import kr.co.yourplanet.core.alimtalk.support.AlimTalkVariableResolver;
import kr.co.yourplanet.core.entity.alimtalk.AlimTalkTemplate;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.project.Project;
import kr.co.yourplanet.core.enums.AlimTalkTemplateCode;
import kr.co.yourplanet.online.business.project.repository.ProjectRepository;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BusinessAlimTalkSendService {

    private final AlimTalkSendService alimTalkSendService;
    private final AlimTalkTemplateRepository alimTalkTemplateRepository;
    private final AlimTalkSendFormFactory alimTalkSendFormFactory;

    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    private static final String ALIM_TALK_SEND_REQUEST_SUCCESS = "A000";
    private static final String ALIM_TALK_SEND_REQUEST_ERROR = "XXXX";
    private static final String ETC_VARIABLE_MAP_KEY = "etcMap";


    /**
     * 알림톡 발송 공통 메소드
     *
     * @param alimTalkTemplateCode [알림톡템플릿]
     * @param targetMember [알림톡수신자]
     * @param contextObjectMap [변수치환맵]
     * @return AlimTalkSendResponseForm [알림톡 발송요청 결과 확인이 필요한 경우 사용 (ALIM_TALK_SEND_REQUEST_SUCCESS = "A000")]
     */
    private AlimTalkSendResponseForm send(AlimTalkTemplateCode alimTalkTemplateCode, Member targetMember, Map<String, Object> contextObjectMap) {
        Optional<AlimTalkTemplate> alimTalkTemplateOptional = alimTalkTemplateRepository.findByTemplateCode(
            alimTalkTemplateCode.getCode());

        if (alimTalkTemplateOptional.isPresent()) {
            AlimTalkTemplate alimTalkTemplate = alimTalkTemplateOptional.get();

            // 알림톡 문구 변수치환
            String messageText = AlimTalkVariableResolver.resolve(alimTalkTemplate.getText(), contextObjectMap);
            AlimTalkSendForm alimTalkSendForm = alimTalkSendFormFactory.createFromTemplate(alimTalkTemplate,
                targetMember.getTel(), messageText);

            try {
                // 알림톡 발송요청
                return alimTalkSendService.sendAlimTalk(alimTalkSendForm, targetMember.getId());
            } catch (JacksonException e) {
                log.error("{} 알림톡 발송 실패 : JacksonException", alimTalkTemplateCode.getDescription());
                return AlimTalkSendResponseForm.builder().code(ALIM_TALK_SEND_REQUEST_ERROR).build();
            } catch (Exception e) {
                log.error("{} 알림톡 발송 실패 : {}", alimTalkTemplateCode.getDescription(), e.getMessage());
                return AlimTalkSendResponseForm.builder().code(ALIM_TALK_SEND_REQUEST_ERROR).build();
            }
        } else {
            log.error("{} 알림톡 발송 실패 : {} 템플릿 미존재", alimTalkTemplateCode.getDescription(),
                alimTalkTemplateCode.getCode());
            return AlimTalkSendResponseForm.builder().code(ALIM_TALK_SEND_REQUEST_ERROR).build();
        }

    }

    /**
     * [회원가입] 가입 완료 알림톡 발송
     * [수신자] 회원가입 완료자
     *
     * @param targetMember [알림톡수신자]
     */
    @Async
    public void sendMemberJoinCompleteAlimTalk(Member targetMember) {
        // 1. 템플릿코드 지정
        AlimTalkTemplateCode templateCode = switch (targetMember.getMemberType()) {
            case CREATOR -> AlimTalkTemplateCode.MEMBER_JOIN_CREATOR;
            case SPONSOR -> AlimTalkTemplateCode.MEMBER_JOIN_SPONSOR;
            default -> null;
        };

        if (templateCode == null) {
            return;
        }
        // 알림톡 문구 변수 치환 객체 주입
        Map<String, Object> contextObjectMap = Map.of("member", targetMember);
        send(templateCode, targetMember, contextObjectMap);

    }

    /**
     * [프로젝트] 협상 접수 알림톡 발송
     * [수신자] 협상 검토자(작가 or 광고주)
     *
     * @param targetMemberId [알림톡수신자]
     * @param sponsorId [광고주]
     * @param projectId [프로젝트]
     */
    @Async
    public void sendProjectNegotiationCommon(Long targetMemberId, Long sponsorId, Long projectId) {
        AlimTalkTemplateCode templateCode = AlimTalkTemplateCode.PROJECT_NEGOTIATE_COMMON;

        Optional<Member> optionalTargetMember = memberRepository.findById(targetMemberId);
        Optional<Member> optionalSponsorMember = memberRepository.findById(sponsorId);
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalTargetMember.isPresent() && optionalSponsorMember.isPresent() && optionalProject.isPresent()) {
            Member targetMember = optionalTargetMember.get();
            Member sponsor = optionalSponsorMember.get();
            Project project = optionalProject.get();

            // 알림톡 문구 변수 치환 객체 주입
            Map<String, Object> contextObjectMap = Map.of("member", targetMember, "sponsor", sponsor, "project",
                project);

            send(templateCode, targetMember, contextObjectMap);

        }

    }

    /**
     * [프로젝트] 의뢰 수락 알림톡 발송
     * [수신자] 작가, 광고주
     *
     * @param creatorId [작가]
     * @param sponsorId [광고주]
     * @param projectId [프로젝트]
     */
    @Async
    public void sendProjectAcceptCommon(Long creatorId, Long sponsorId, Long projectId) {

        Optional<Member> optionalCreatorMember = memberRepository.findById(creatorId);
        Optional<Member> optionalSponsorMember = memberRepository.findById(sponsorId);
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalCreatorMember.isPresent() && optionalSponsorMember.isPresent() && optionalProject.isPresent()) {
            Member creator = optionalCreatorMember.get();
            Member sponsor = optionalSponsorMember.get();
            Project project = optionalProject.get();

            // 알림톡 문구 변수 치환 객체 주입
            Map<String, Object> contextObjectMap = Map.of("creator", creator, "sponsor", sponsor, "project", project);

            // 작가에게 알림톡 발송
            send(AlimTalkTemplateCode.PROJECT_ACCEPT_CREATOR, creator, contextObjectMap);

            // 광고주에게 알림톡 발송
            send(AlimTalkTemplateCode.PROJECT_ACCEPT_SPONSOR, sponsor, contextObjectMap);
        }

    }

    /**
     * [프로젝트] 의뢰 거절 알림톡 발송
     * [수신자] 광고주
     *
     * @param sponsorId [광고주]
     * @param creatorId [작가]
     * @param projectId [프로젝트]
     */
    @Async
    public void sendProjectRejectSponsor(Long sponsorId, Long creatorId, Long projectId) {

        Optional<Member> optionalCreatorMember = memberRepository.findById(creatorId);
        Optional<Member> optionalSponsorMember = memberRepository.findById(sponsorId);
        Optional<Project> optionalProject = projectRepository.findById(projectId);

        if (optionalCreatorMember.isPresent() && optionalSponsorMember.isPresent() && optionalProject.isPresent()) {
            Member creator = optionalCreatorMember.get();
            Member sponsor = optionalSponsorMember.get();
            Project project = optionalProject.get();

            // 알림톡 문구 변수 치환 객체 주입
            Map<String, Object> contextObjectMap = Map.of("creator", creator, "sponsor", sponsor, "project", project);

            // 작가에게 알림톡 발송
            send(AlimTalkTemplateCode.PROJECT_REJECT_SPONSOR, sponsor, contextObjectMap);
        }

    }

    /**
     * [프로젝트] 의뢰 취소 알림톡 발송
     * [수신자] 작가
     *
     * @param creatorId [작가]
     */
    @Async
    public void sendProjectCancelCreator(Long creatorId) {
        Optional<Member> optionalCreatorMember = memberRepository.findById(creatorId);

        if(optionalCreatorMember.isPresent()) {
            Member creator = optionalCreatorMember.get();

            // 알림톡 문구 변수 치환 객체 주입
            Map<String, Object> contextObjectMap = Map.of("creator", creator);

            // 작가에게 알림톡 발송
            send(AlimTalkTemplateCode.PROJECT_CANCEL_CREATOR, creator, contextObjectMap);
        }

    }

    /**
     * [계정] 인증코드 발송
     * [수신자] 사용자
     *
     * @param memberId 멤버ID
     */
    public void sendAuthCode(Long memberId, String authCode) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // 엔티티에 포함되지 않은 변수 처리를 위해 key:ETC_VARIABLE_MAP_KEY, value:Map 처리
            // 알림톡 문구 변수 치환 객체 주입
            Map<String, Object> contextObjectMap = Map.of(ETC_VARIABLE_MAP_KEY, Map.of("authCode", authCode));

            // 멤버에게 알림톡 발송
            send(AlimTalkTemplateCode.AUTH_CODE, member, contextObjectMap);
        }

    }

}
