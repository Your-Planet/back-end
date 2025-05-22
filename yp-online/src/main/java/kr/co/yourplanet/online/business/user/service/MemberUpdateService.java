package kr.co.yourplanet.online.business.user.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.MemberBasicInfo;
import kr.co.yourplanet.core.entity.member.SettlementInfo;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.FileType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.business.file.service.FileService;
import kr.co.yourplanet.online.business.user.dto.request.BaseUpdateForm;
import kr.co.yourplanet.online.business.user.dto.request.CreatorUpdateForm;
import kr.co.yourplanet.online.business.user.dto.request.MemberUpdateForm;
import kr.co.yourplanet.online.business.user.dto.request.SettlementForm;
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
    private final FileService fileService;

    public void updateMember(Long memberId, MemberUpdateForm memberUpdateForm) {
        Member member = memberQueryService.getById(memberId);

        validateUpdateRequirements(member, memberUpdateForm);

        Member updatedMember = createUpdateMember(member, memberUpdateForm);
        memberRepository.saveMember(updatedMember);

        if (member.isBusinessCreator()) {
            upsertSettlementFile(member, memberUpdateForm.getCreatorUpdateForm().getSettlementForm());
        }
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
            SettlementForm form = creatorUpdateForm.getSettlementForm();
            builder.settlementInfo(memberCreateService.createSettlementInfo(form));
        }

        if (BusinessType.BUSINESS.equals(businessType)) {
            builder.businessInfo(memberCreateService.createBusinessInfo(baseForm.getBusinessForm()));
        }

        return builder.build();
    }

    private void validateUpdateRequirements(Member member, MemberUpdateForm memberUpdateForm) {
        BusinessType businessType = memberUpdateForm.getBaseUpdateForm().getBusinessType();

        validateByMemberType(member, memberUpdateForm);
        validateByBusinessType(businessType, memberUpdateForm);
    }

    private void validateByMemberType(Member member, MemberUpdateForm memberUpdateForm) {
        if (member.isCreator()) {
            CreatorUpdateForm creatorForm = memberUpdateForm.getCreatorUpdateForm();
            if (creatorForm == null) {
                throw new BadRequestException("작가 회원 정보 수정의 경우 작가 정보를 포함해야 합니다.");
            }

            SettlementForm settlementForm = creatorForm.getSettlementForm();
            if (settlementForm == null) {
                throw new BadRequestException("작가 회원 정보 수정의 경우 정산 정보를 포함해야 합니다.");
            }

            if (memberUpdateForm.getBaseUpdateForm().getBusinessType() != settlementForm.getBusinessType()) {
                throw new BadRequestException("기본 정보의 사업자 여부와 정산 정보의 사업자 여부가 다릅니다.");
            }
        }
    }

    private void validateByBusinessType(BusinessType businessType, MemberUpdateForm updateForm) {
        switch (businessType) {
            case BUSINESS -> validateBusinessMember(updateForm.getBaseUpdateForm(), updateForm.getCreatorUpdateForm());
            case INDIVIDUAL -> validateIndividualMember(updateForm.getCreatorUpdateForm());
        }
    }

    private void validateBusinessMember(BaseUpdateForm baseForm, CreatorUpdateForm creatorForm) {
        if (baseForm.getBusinessForm() == null) {
            throw new BadRequestException("사업자 회원은 사업자 정보를 반드시 입력해야 합니다.");
        }

        if (creatorForm != null) {
            SettlementForm settlementForm = creatorForm.getSettlementForm();
            if (settlementForm == null
                    || settlementForm.getBankAccountCopyFileId() == null
                    || settlementForm.getBusinessLicenseFileId() == null) {
                throw new BadRequestException("사업자 회원은 사업자 정산 정보를 반드시 입력해야 합니다.");
            }
        }
    }

    private void validateIndividualMember(CreatorUpdateForm creatorForm) {
        if (creatorForm != null) {
            SettlementForm settlementForm = creatorForm.getSettlementForm();
            if (settlementForm == null || settlementForm.getRrn() == null) {
                throw new BadRequestException("개인 회원은 개인 정산 정보를 반드시 입력해야 합니다.");
            }
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

    private void upsertSettlementFile(Member member, SettlementForm form) {
        if (form == null) {
            return;
        }

        long uploaderId = member.getId();

        SettlementInfo existing = member.getSettlementInfo();
        Long newBankFileId = form.getBankAccountCopyFileId();
        Long newLicenseFileId = form.getBusinessLicenseFileId();

        if (existing != null) {
            replaceSettlementFileIfChanged(existing.getBankAccountCopyFileId(), newBankFileId, uploaderId);
            replaceSettlementFileIfChanged(existing.getBusinessLicenseFileId(), newLicenseFileId, uploaderId);
        } else {
            fileService.completeUpload(newBankFileId, uploaderId, uploaderId, FileType.SETTLEMENT_FILE);
            fileService.completeUpload(newLicenseFileId, uploaderId, uploaderId, FileType.SETTLEMENT_FILE);
        }
    }

    private void replaceSettlementFileIfChanged(Long oldId, Long newId, long uploaderId) {
        if (!Objects.equals(oldId, newId)) {
            fileService.replace(oldId, newId, uploaderId, uploaderId, FileType.SETTLEMENT_FILE);
        }
    }
}
