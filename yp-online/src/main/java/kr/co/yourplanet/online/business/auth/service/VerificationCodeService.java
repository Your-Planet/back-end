package kr.co.yourplanet.online.business.auth.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.enums.AuthenticationMethod;
import kr.co.yourplanet.core.util.RandomTokenGenerator;
import kr.co.yourplanet.core.util.VerificationCodeGenerator;
import kr.co.yourplanet.online.business.auth.dto.AuthenticationTokenInfo;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeCheckForm;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeData;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeSendCommand;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeSendForm;
import kr.co.yourplanet.online.business.auth.repository.TokenRepository;
import kr.co.yourplanet.online.business.auth.repository.VerificationCodeRepository;
import kr.co.yourplanet.online.business.auth.service.sender.VerificationSender;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.common.exception.BadRequestException;
import kr.co.yourplanet.online.common.exception.InternalServerErrorException;

@Transactional
@Service
public class VerificationCodeService {

    private final MemberQueryService memberQueryService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final TokenRepository tokenRepository;

    private final Map<AuthenticationMethod, VerificationSender> senderMap;

    public VerificationCodeService(
            MemberQueryService memberQueryService,
            VerificationCodeRepository verificationCodeRepository,
            TokenRepository tokenRepository,
            List<VerificationSender> senders) {
        this.memberQueryService = memberQueryService;
        this.verificationCodeRepository = verificationCodeRepository;
        this.tokenRepository = tokenRepository;
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(VerificationSender::getMethod, sender -> sender));
    }

    public void sendVerificationCode(VerificationCodeSendForm form) {
        VerificationCodeSendCommand command = createSendCommand(form);

        VerificationSender sender = Optional.ofNullable(senderMap.get(command.method()))
                .orElseThrow(() -> new InternalServerErrorException("지원하지 않는 인증 방식입니다: " + command.method()));

        sender.send(command);
        verificationCodeRepository.save(command.purpose(), command.destination(), command.verificationCode(), command.memberId());
    }

    public AuthenticationTokenInfo checkVerificationCode(VerificationCodeCheckForm form) {
        VerificationCodeData codeData = verificationCodeRepository.get(form.destination())
                .orElseThrow(() -> new BadRequestException("해당 인증 요청 대상에 대한 인증번호 정보가 없습니다."));

        // 인증번호 확인
        if (!codeData.code().equals(form.verificationCode())) {
            throw new BadRequestException("인증 번호가 일치하지 않습니다.");
        }

        // 사용한 인증번호 삭제
        verificationCodeRepository.delete(form.destination());

        String token = RandomTokenGenerator.generate();
        tokenRepository.save(codeData.purpose(), token, codeData.memberId());

        return new AuthenticationTokenInfo(token);
    }

    private VerificationCodeSendCommand createSendCommand(VerificationCodeSendForm form) {
        String verificationCode = VerificationCodeGenerator.generate();

        switch (form.method()) {
            case EMAIL -> {
                String email = Optional.ofNullable(form.email())
                        .orElseThrow(() -> new BadRequestException("이메일을 입력해주세요."));
                long memberId = memberQueryService.getIdByEmail(email).getId();

                return new VerificationCodeSendCommand(form.purpose(), form.method(), email, verificationCode, memberId);
            }

            // TODO: 알림톡, SMS 등의 인증 수단 추가 예정
        }

        throw new NotImplementedException("아직 구현되지 않은 기능입니다.");
    }
}
