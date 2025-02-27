package com.x.bp.core.dto.order;

import lombok.Data;

@Data
public class OrderStatusUpdateReq {
    private Long id;
    private Integer status;
}
