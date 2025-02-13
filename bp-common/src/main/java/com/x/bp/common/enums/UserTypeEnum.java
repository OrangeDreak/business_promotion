package com.x.bp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xuanruo
 * @email xuanruo@jingling.group
 * @date 2023/11/30
 **/
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    CUSTOMER(0, "客户"),
    ADMINISTRATOR(1, "管理员")

    ;
    private Integer type;
    private String desc;

    public static boolean isValidType(Integer type) {
        return CUSTOMER.getType().equals(type) || ADMINISTRATOR.getType().equals(type);
    }
}
