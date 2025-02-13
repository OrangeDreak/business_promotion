package com.x.bp.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录类型
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Getter
@AllArgsConstructor
public enum UserLoginTypeEnum {
    MOBILE(0, "手机号"),
    EMAIL(1, "邮箱"),
    ;

    private Integer code;

    private String desc;

    public static String getValueByCode(Integer code) {
        for (UserLoginTypeEnum userLoginTypeEnum : UserLoginTypeEnum.values()) {
            if (userLoginTypeEnum.getCode().equals(code)) {
                return userLoginTypeEnum.getDesc();
            }
        }
        return null;
    }
}
