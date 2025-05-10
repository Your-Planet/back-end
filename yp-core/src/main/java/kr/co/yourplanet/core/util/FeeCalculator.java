package kr.co.yourplanet.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeeCalculator {

    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    // 수수료 금액 계산
    public static long calculateFee(long totalAmount, int commissionRate) {
        validateAmount(totalAmount);
        validateRate(commissionRate);

        BigDecimal amount = BigDecimal.valueOf(totalAmount);
        BigDecimal rate = BigDecimal.valueOf(commissionRate);

        return amount
                .multiply(rate)
                .divide(ONE_HUNDRED, 0, RoundingMode.HALF_UP)
                .longValueExact();
    }

    // 정산 금액 계산
    // 정산 금액 (총 금액  - 수수료)
    public static long calculateSettlementAmount(long totalAmount, int commissionRate) {
        return totalAmount - calculateFee(totalAmount, commissionRate);
    }


    private static void validateAmount(long amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("총 금액은 음수일 수 없습니다.");
        }
    }

    private static void validateRate(int rate) {
        if (rate < 0 || rate > 100) {
            throw new IllegalArgumentException("수수료율은 0 이상 100 이하이어야 합니다.");
        }
    }
}
