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
    WAIT_PAY(0, "待付款"),
    WAIT_DELIVERY(1, "待发货"),
    WAIT_RECEIVE(2, "待收货"),
    WAIT_COMMENT(3, "待评价"),
    FINISH(4, "已完成"),
    CANCEL(5, "已取消"),
    REFUND(6, "退款中"),
    REFUND_SUCCESS(7, "退款成功"),
    REFUND_FAIL(8, "退款失败"),
    REFUND_CANCEL(9, "退款取消"),
    ;

    private Integer status;
    private String desc;
}

