package kr.co.yourplanet.online.business.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberSalt;
import kr.co.yourplanet.core.entity.member.MemberBasicInfo;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.business.user.dto.BaseJoinForm;
import kr.co.yourplanet.online.business.user.dto.CreatorJoinForm;
import kr.co.yourplanet.online.business.user.dto.InstagramForm;
import kr.co.yourplanet.online.business.user.dto.MemberJoinForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.business.user.repository.MemberSaltRepository;
import kr.co.yourplanet.online.common.encrypt.EncryptManager;
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

    public void join(MemberJoinForm joinForm) {
        BaseJoinForm baseForm = joinForm.getBaseJoinForm();
        validateJoin(joinForm);

        String salt = encryptManager.generateSalt();
        String encodedHashPassword = encryptManager.encryptPassword(baseForm.getPassword(), salt);

        Member member = createJoinMember(joinForm, encodedHashPassword);
        memberRepository.saveMember(member);

        createMemberSalt(member, salt);
    }

    private void validateJoin(MemberJoinForm joinForm) {
        BaseJoinForm baseForm = joinForm.getBaseJoinForm();

        memberValidationService.checkDuplicateEmail(baseForm.getEmail());
        memberValidationService.validatePassword(baseForm.getPassword());

        if (MemberType.CREATOR.equals(baseForm.getMemberType())) {
            InstagramForm instagramForm = joinForm.getCreatorJoinForm().getInstagramForm();

            memberValidationService.checkDuplicateInstagramId(instagramForm.instagramId());
        }
    }

    private Member createJoinMember(MemberJoinForm joinForm, String encodedHashPassword) {
        BaseJoinForm baseForm = joinForm.getBaseJoinForm();
        CreatorJoinForm creatorJoinForm = joinForm.getCreatorJoinForm();

        MemberType memberType = baseForm.getMemberType();
        BusinessType businessType = baseForm.getBusinessType();

        Member.MemberBuilder builder = Member.builder()
                .memberType(memberType)
                .accountInfo(Member.createAccountInfo(baseForm.getEmail(), encodedHashPassword))
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

    private void createMemberSalt(Member member, String salt) {
        MemberSalt memberSalt = MemberSalt.builder()
                .member(member)
                .salt(encryptManager.encryptSalt(salt))
                .build();

        memberSaltRepository.saveMemberSalt(memberSalt);
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
