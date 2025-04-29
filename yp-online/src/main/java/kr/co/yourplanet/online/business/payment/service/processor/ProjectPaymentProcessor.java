package kr.co.yourplanet.online.business.payment.service.processor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.project.service.ProjectValidationService;
import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;
import kr.co.yourplanet.online.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProjectPaymentProcessor implements PaymentProcessor {

    private final ProjectValidationService projectValidationService;

    @Override
    public void validate(Long targetId, Long memberId) {
        projectValidationService.checkExist(targetId);
        projectValidationService.checkSponsor(targetId, memberId);
        projectValidationService.checkInProgress(targetId);
    }

    @Override
    public void afterPayment(PaymentResponse response, Long targetId) {
        if (!response.isSuccess()) {
            throw new BusinessException(StatusCode.INTERNAL_SERVER_ERROR, "결제에 실패하여 후처리가 불가능 합니다.", false);
        }

        // TODO: 정산 상태 처리 (결제 일시, 상태 변경)
    }
}