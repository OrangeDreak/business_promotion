package com.x.bp.common.utils;

import com.x.bp.common.constant.NumberConstants;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zouzhe
 * @CreateDate 2024/1/29 23:14
 * @Description
 */
@Slf4j
public final class PriceUtil {
    private PriceUtil() {

    }

    public static BigDecimal fen2yuan(Long g) {
        if (g == null || g == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(g)
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }

    public static Long yuan2fen(BigDecimal kg) {
        if (kg == null || kg.compareTo(new BigDecimal(NumberConstants.NUMBER_ZERO)) == 0) {
            return 0L;
        }
        return kg.multiply(BigDecimal.valueOf(100))
            .setScale(0, RoundingMode.UP)
            .longValue();
    }
}
