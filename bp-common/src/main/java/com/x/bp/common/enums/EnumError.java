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
public enum EnumError {

    // 用户
    USER_NOT_LOGIN(4001, "用户未登录", "User is not logged in"),// 用户未登录
    USER_NOT_EXISTS(6023, "账号不存在", "Account does not exist"),// 账号不存在
    PASSWORD_ERROR(6024, "密码错误", "Password error"),// 密码错误，还剩%d次机会
    EMAIL_EXIST(6028, "邮箱已存在", "Email already exists"),// 邮箱已存在

    ;

    /**
     * 值
     */
    private int value;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称
     */
    private String nameEn;

}