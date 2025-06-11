package kr.co.yourplanet.online.business.auth.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.enums.AuthPurpose;
import kr.co.yourplanet.core.enums.AuthMethod;
import kr.co.yourplanet.core.util.RandomTokenGenerator;
import kr.co.yourplanet.core.util.AuthCodeGenerator;
import kr.co.yourplanet.online.business.auth.dto.AuthTokenInfo;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeCheckForm;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeData;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeSendCommand;
import kr.co.yourplanet.online.business.auth.dto.AuthCodeSendForm;
import kr.co.yourplanet.online.business.auth.repository.AuthCodeRepository;
import kr.co.yourplanet.online.business.auth.repository.AuthTokenRepository;
import kr.co.yourplanet.online.business.auth.service.sender.AuthCodeSender;
import kr.co.yourplanet.online.business.user.service.MemberQueryService;
import kr.co.yourplanet.online.common.exception.BadRequestException;
import kr.co.yourplanet.online.common.exception.InternalServerErrorException;

@Transactional
@Service
public class AuthCodeService {

    private final MemberQueryService memberQueryService;
    private final AuthCodeRepository authCodeRepository;
    private final AuthTokenRepository authTokenRepository;

    private final Map<AuthMethod, AuthCodeSender> senderMap;

    public AuthCodeService(
            MemberQueryService memberQueryService,
            AuthCodeRepository authCodeRepository,
            AuthTokenRepository authTokenRepository,
            List<AuthCodeSender> senders) {
        this.memberQueryService = memberQueryService;
        this.authCodeRepository = authCodeRepository;
        this.authTokenRepository = authTokenRepository;
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(AuthCodeSender::getMethod, sender -> sender));
    }

    public void sendAuthCode(AuthCodeSendForm form) {
        AuthCodeSendCommand command = createSendCommand(form);

        // 인증 수단에 맞는 sender 가져오기
        AuthCodeSender sender = Optional.ofNullable(senderMap.get(command.method()))
                .orElseThrow(() -> new InternalServerErrorException("지원하지 않는 인증 방식입니다: " + command.method()));

        // 인증번호 전송 및 저장
        sender.send(command);
        authCodeRepository.save(command.purpose(), command.destination(), command.authCode(), command.memberId());
    }

    public AuthTokenInfo checkAuthCode(AuthCodeCheckForm form) {
        AuthCodeData authData = authCodeRepository.get(form.destination())
                .orElseThrow(() -> new BadRequestException("해당 인증 요청 대상에 대한 인증번호 정보가 없습니다."));

        // 인증번호 확인
        if (!authData.code().equals(form.authCode())) {
            throw new BadRequestException("인증 번호가 일치하지 않습니다.");
        }

        // 토큰 발급
        String authToken = RandomTokenGenerator.generate();
        authTokenRepository.save(authData.purpose(), authToken, authData.memberId());

        // 사용된 인증번호 삭제
        authCodeRepository.delete(form.destination());

        return new AuthTokenInfo(authToken);
    }

    private AuthCodeSendCommand createSendCommand(AuthCodeSendForm form) {
        String authCode = AuthCodeGenerator.generate();
        AuthPurpose purpose = form.purpose();
        AuthMethod method = form.method();

        String target;
        Long memberId;

        switch (form.method()) {
            case EMAIL -> {
                target = Optional.ofNullable(form.email())
                        .orElseThrow(() -> new BadRequestException("이메일을 입력해주세요."));
                memberId = memberQueryService.getByEmail(target).getId();
            }
            case ALIMTALK -> {
                target = Optional.ofNullable(form.tel())
                        .orElseThrow(() -> new BadRequestException("전화번호를 입력해주세요."));
                memberId = memberQueryService.getByTel(target).getId();
            }
            default -> throw new NotImplementedException("아직 구현되지 않은 기능입니다.");
        }

        return new AuthCodeSendCommand(purpose, method, target, authCode, memberId);
    }
}
