package com.x.bp.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumRedisKeys {
    PASSWORD_ERROR_COUNT("password:error:%s", "密码输入错误次数"),

    PASSWORD_ERROR_TODAY_COUNT("password:error:today:%s", "当天密码输入错误次数"),
    ;


    private String key;
    private String desc;

}
