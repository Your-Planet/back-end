package kr.co.yourplanet.online.business.auth.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.enums.AuthenticationMethod;
import kr.co.yourplanet.core.util.VerificationCodeGenerator;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeSendCommand;
import kr.co.yourplanet.online.business.auth.dto.VerificationCodeSendForm;
import kr.co.yourplanet.online.business.auth.repository.VerificationCodeRepository;
import kr.co.yourplanet.online.business.auth.service.sender.VerificationSender;
import kr.co.yourplanet.online.business.user.service.MemberValidationService;
import kr.co.yourplanet.online.common.exception.BadRequestException;
import kr.co.yourplanet.online.common.exception.InternalServerErrorException;

@Transactional
@Service
public class VerificationCodeService {

    private final MemberValidationService memberValidationService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final Map<AuthenticationMethod, VerificationSender> senderMap;

    public VerificationCodeService(
            MemberValidationService memberValidationService,
            VerificationCodeRepository verificationCodeRepository,
            List<VerificationSender> senders
    ) {
        this.memberValidationService = memberValidationService;
        this.verificationCodeRepository = verificationCodeRepository;
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(VerificationSender::getMethod, sender -> sender));
    }

    public void sendVerificationCode(VerificationCodeSendForm form) {
        VerificationCodeSendCommand command = createSendCommand(form);

        VerificationSender sender = Optional.ofNullable(senderMap.get(command.method()))
                .orElseThrow(() -> new InternalServerErrorException("지원하지 않는 인증 방식입니다: " + command.method()));

        sender.send(command);
        verificationCodeRepository.save(command.destination(), command.verificationCode());
    }

    private VerificationCodeSendCommand createSendCommand(VerificationCodeSendForm form) {
        String verificationCode = VerificationCodeGenerator.generate();

        switch (form.method()) {
            case EMAIL -> {
                String email = Optional.ofNullable(form.email())
                        .orElseThrow(() -> new BadRequestException("이메일을 입력해주세요."));
                memberValidationService.checkEmailExists(email);

                return new VerificationCodeSendCommand(form.method(), email, verificationCode);
            }
        }

        throw new NotImplementedException("아직 구현되지 않은 기능입니다.");
    }
}
