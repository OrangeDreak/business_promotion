package com.x.bp.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
public class LengthUtil {

    private LengthUtil() {
    }

    public static Double mm2cm(Long mm) {
        if (mm == null || mm == 0) {
            return 0.0;
        }
        return BigDecimal.valueOf(mm)
                .divide(BigDecimal.valueOf(10), 1, RoundingMode.HALF_EVEN)
                .doubleValue();
    }

    public static Long cm2mm(Double cm) {
        if (cm == null || cm == 0) {
            return 0L;
        }
        return BigDecimal.valueOf(cm)
                .multiply(BigDecimal.valueOf(10))
                .longValue();
    }
}
