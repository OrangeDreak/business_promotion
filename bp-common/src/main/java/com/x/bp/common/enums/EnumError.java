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
    PARAMETER_ERROR(1001, "参数错误", "Parameter error"),

    // 用户
    USER_NOT_LOGIN(4001, "用户未登录", "User is not logged in"),// 用户未登录
    USER_NOT_EXISTS(6023, "账号不存在", "Account does not exist"),// 账号不存在
    PASSWORD_ERROR(6024, "密码错误", "Password error"),// 密码错误，还剩%d次机会
    EMAIL_EXIST(6028, "邮箱已存在", "Email already exists"),// 邮箱已存在

    //商品

    PLATFORM_INVALID(7001, "无效的平台", "Invalid Platform"),// 无效的平台
    EXCHANGE_RATE_GET_FAIL(7002, "获取汇率失败", "Exchange rate acquisition failed"),// 无效的平台
    PRODUCT_SOLD_OUT(7003, "商品库存不足", "Insufficient inventory of goods"),
    PRODUCT_TAKEN_DOWN(7004, "商品已下架", "The product has been taken down"),

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