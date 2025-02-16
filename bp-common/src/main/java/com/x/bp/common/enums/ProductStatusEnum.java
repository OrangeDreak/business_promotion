package com.x.bp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {

    ON_SALE(1, "上架"),
    OFF_SALE(0, "下架")

    ;
    private Integer status;
    private String desc;
}
