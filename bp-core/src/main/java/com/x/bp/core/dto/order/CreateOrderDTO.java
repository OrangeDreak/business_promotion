package com.x.bp.core.dto.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:32
 * @Description
 */
@Data
public class CreateOrderDTO {
    private List<Long> orderIdList;

    private BigDecimal totalAmount;
}
