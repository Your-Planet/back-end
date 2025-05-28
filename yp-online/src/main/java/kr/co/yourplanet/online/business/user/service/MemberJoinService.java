package kr.co.yourplanet.online.business.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberSalt;
import kr.co.yourplanet.core.entity.member.MemberBasicInfo;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.business.alimtalk.util.BusinessAlimTalkSendService;
import kr.co.yourplanet.online.business.user.dto.request.BaseJoinForm;
import kr.co.yourplanet.online.business.user.dto.request.CreatorJoinForm;
import kr.co.yourplanet.online.business.user.dto.request.MemberJoinForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.business.user.repository.MemberSaltRepository;
import kr.co.yourplanet.online.common.encrypt.EncryptManager;
import kr.co.yourplanet.online.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberJoinService {

    private final MemberCreateService memberCreateService;
    private final MemberValidationService memberValidationService;

    private final MemberRepository memberRepository;
    private final MemberSaltRepository memberSaltRepository;

    private final EncryptManager encryptManager;

    private final BusinessAlimTalkSendService businessAlimTalkSendService;

    public void join(MemberJoinForm joinForm) {
        validateJoinRequirements(joinForm);

        String salt = encryptManager.generateSalt();
        Member member = createJoinMember(joinForm, salt);
        memberRepository.saveMember(member);

        MemberSalt memberSalt = createMemberSalt(member, salt);
        memberSaltRepository.saveMemberSalt(memberSalt);

        businessAlimTalkSendService.sendMemberJoinCompleteAlimTalk(member);
    }

    private void validateJoinRequirements(MemberJoinForm joinForm) {
        BaseJoinForm baseForm = joinForm.getBaseJoinForm();

        memberValidationService.checkDuplicateEmail(baseForm.getEmail());
        memberValidationService.validatePasswordFormat(baseForm.getPassword());

        if (BusinessType.BUSINESS.equals(baseForm.getBusinessType()) && baseForm.getBusinessForm() == null) {
            throw new BadRequestException("사업자 회원은 사업자 정보를 반드시 입력해야 합니다.");
        }

        if (MemberType.CREATOR.equals(baseForm.getMemberType()) && joinForm.getCreatorJoinForm() == null) {
            throw new BadRequestException("작가 가입을 위해 필요한 정보가 누락되었습니다.");
        }
    }

    private Member createJoinMember(MemberJoinForm joinForm, String salt) {
        BaseJoinForm baseForm = joinForm.getBaseJoinForm();
        CreatorJoinForm creatorJoinForm = joinForm.getCreatorJoinForm();

        MemberType memberType = baseForm.getMemberType();
        BusinessType businessType = baseForm.getBusinessType();

        String encryptPassword = encryptManager.encryptPassword(baseForm.getPassword(), salt);

        Member.MemberBuilder builder = Member.builder()
                .memberType(memberType)
                .accountInfo(Member.createAccountInfo(baseForm.getEmail(), encryptPassword))
                .memberBasicInfo(createMemberBasicInfo(joinForm))
                .agreementInfo(memberCreateService.createAgreementInfo(baseForm.getTermsForm()));

        if (MemberType.CREATOR.equals(memberType)) {
            builder.instagramInfo(memberCreateService.createInstagramInfo(creatorJoinForm.getInstagramForm()));
        }

        if (BusinessType.BUSINESS.equals(businessType)) {
            builder.businessInfo(memberCreateService.createBusinessInfo(baseForm.getBusinessForm()));
        }

        return builder.build();
    }

    private MemberSalt createMemberSalt(Member member, String salt) {
        return  MemberSalt.builder()
                .member(member)
                .salt(encryptManager.encryptSalt(salt))
                .build();
    }

    private MemberBasicInfo createMemberBasicInfo(MemberJoinForm joinForm) {
        BaseJoinForm baseForm = joinForm.getBaseJoinForm();
        CreatorJoinForm creatorJoinForm = joinForm.getCreatorJoinForm();

        return Member.createBasicInfo(
                baseForm.getMemberType(),
                baseForm.getBusinessType(),
                baseForm.getName(),
                baseForm.getTel(),
                baseForm.getBirthDate(),
                creatorJoinForm != null ? creatorJoinForm.getGenderType() : null
        );
    }
}
