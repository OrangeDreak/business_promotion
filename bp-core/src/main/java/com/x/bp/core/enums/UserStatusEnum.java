package com.x.bp.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 用户状态枚举
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    NORMAL(0, "正常"),

    FREEZE(1, "账号已被冻结"),

    NOT_ACTIVE(2, "账号待激活"),

    LOG_OFF(3, "账号已被注销"),

    ;

    private final Integer code;
    private final String desc;

    public static UserStatusEnum getByCode(Integer code) {
        return Arrays.stream(values())
                .filter(s -> Objects.equals(s.getCode(), code))
                .findFirst()
                .orElse(null);
    }

    public static String getDescByCode(Integer code) {
        return Arrays.stream(values())
                .filter(s -> Objects.equals(s.getCode(), code))
                .map(UserStatusEnum::getDesc)
                .findFirst()
                .orElse("");
    }
}
