package kr.co.yourplanet.online.business.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberBasicInfo;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.business.user.dto.update.BaseUpdateForm;
import kr.co.yourplanet.online.business.user.dto.update.CreatorUpdateForm;
import kr.co.yourplanet.online.business.user.dto.update.MemberUpdateForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberCreateService memberCreateService;
    private final MemberQueryService memberQueryService;

    private final MemberRepository memberRepository;

    public void updateMember(Long memberId, MemberUpdateForm memberUpdateForm) {
        Member member = memberQueryService.getById(memberId);

        validateUpdate(member, memberUpdateForm);

        Member updatedMember = createUpdateMember(member, memberUpdateForm);
        memberRepository.saveMember(updatedMember);
    }

    private Member createUpdateMember(Member member, MemberUpdateForm memberUpdateForm) {
        BaseUpdateForm baseForm = memberUpdateForm.getBaseUpdateForm();
        CreatorUpdateForm creatorUpdateForm = memberUpdateForm.getCreatorUpdateForm();

        MemberType memberType = member.getMemberType();
        BusinessType businessType = baseForm.getBusinessType();

        Member.MemberBuilder builder = Member.builder()
                .id(member.getId())
                .memberType(memberType)
                .instagramInfo(member.getInstagramInfo())
                .accountInfo(member.getAccountInfo())
                .agreementInfo(member.getAgreementInfo())
                .memberBasicInfo(updateMemberBasicInfo(memberType, memberUpdateForm))
                .memberSalt(member.getMemberSalt());

        if (MemberType.CREATOR.equals(memberType)) {
            builder.settlementInfo(memberCreateService.createSettlementInfo(creatorUpdateForm.getSettlementForm()));
        }

        if (BusinessType.BUSINESS.equals(businessType)) {
            builder.businessInfo(memberCreateService.createBusinessInfo(baseForm.getBusinessForm()));
        }

        return builder.build();
    }

    private void validateUpdate(Member member, MemberUpdateForm memberUpdateForm) {
        if (member.isCreator() && memberUpdateForm.getCreatorUpdateForm() == null) {
            throw new BadRequestException("창작자 회원 정보 수정의 경우 창작자 정보를 포함해야 합니다.");
        }

        if (member.isCreator() && memberUpdateForm.getCreatorUpdateForm().getSettlementForm() == null) {
            throw new BadRequestException("창작자 회원 정보 수정의 경우 창작자 정산 정보를 포함해야 합니다.");
        }

        if (member.isCreator() && memberUpdateForm.getCreatorUpdateForm().getGenderType() == null) {
            throw new BadRequestException("창작자 회원 정보 수정의 경우 성별 정보를 포함해야 합니다.");
        }
    }

    private MemberBasicInfo updateMemberBasicInfo(MemberType memberType, MemberUpdateForm memberUpdateForm) {
        BaseUpdateForm baseForm = memberUpdateForm.getBaseUpdateForm();
        CreatorUpdateForm creatorUpdateForm = memberUpdateForm.getCreatorUpdateForm();

        return Member.createBasicInfo(
                memberType,
                baseForm.getBusinessType(),
                baseForm.getName(),
                baseForm.getTel(),
                baseForm.getBirthDate(),
                creatorUpdateForm != null ? creatorUpdateForm.getGenderType() : null
        );
    }
}
