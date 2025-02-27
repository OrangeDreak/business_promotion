package com.x.bp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 16:36
 * @Description
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    PENDING(0, "待处理"),
    PAID(1, "已支付"),
    COMPLETED(2, "已完成"),
    CANCEL(3, "已取消"),

    ;

    private Integer status;
    private String desc;
}

