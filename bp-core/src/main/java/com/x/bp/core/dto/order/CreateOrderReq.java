package com.x.bp.core.dto.order;

import lombok.Data;

import java.util.List;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:35
 * @Description
 */
@Data
public class CreateOrderReq {
    private Long userId;
    private Integer platform;
    private String currency;
    private List<Long> skuIdList;
}
