package kr.co.yourplanet.online.business.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.AgreementInfo;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.user.dto.FindIdForm;
import kr.co.yourplanet.online.business.user.dto.MemberDetail;
import kr.co.yourplanet.online.business.user.dto.TermsForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "요청한 멤버를 찾을 수 없습니다.", true));
    }

    public String findId(FindIdForm accountRecoveryFrom) {
        Optional<Member> findMember = memberRepository.findByNameAndPhone(accountRecoveryFrom.getName(), accountRecoveryFrom.getTel());

        if (findMember.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "가입된 회원이 없습니다.", false);
        }

        return findMember.get().getEmail();
    }

    public MemberDetail getMemberDetailInfo(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (findMember.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "해당 회원이 존재하지 않습니다.", false);
        }

        Member member = findMember.get();
        AgreementInfo agreementInfo = member.getAgreementInfo();

        TermsForm termsForm = TermsForm.builder()
                .isTermsOfService(agreementInfo.getTermsOfServiceAgreedTime() != null)
                .isPrivacyPolicy(agreementInfo.getPrivacyPolicyAgreedTime() != null)
                .isShoppingInformation(agreementInfo.getShoppingInformationAgreedTime() != null)
                .build();

        return MemberDetail.builder()
                .email(member.getEmail())
                .name(member.getName())
                .genderType(member.getGenderType())
                .tel(member.getTel())
                .memberType(member.getMemberType())
                .instagramUsername(member.getInstagramInfo().getInstagramUsername())
                .termsForm(termsForm)
                .build();
    }
}
