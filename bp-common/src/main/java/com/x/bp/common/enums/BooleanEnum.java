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
public enum BooleanEnum {

    FALSE(0, "假"),
    TRUE(1, "真")

    ;
    private Integer code;
    private String desc;
}
