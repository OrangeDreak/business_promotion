package com.x.bp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum CurrencyEnum {
    RMB(0, "CNY", "人民币"),
    USD(1, "USD", "美元"),
    ;
    private Integer code;
    private String currency;
    private String desc;

    public static CurrencyEnum getByCode(Integer code) {
        for (CurrencyEnum currencyEnum : values()) {
            if (currencyEnum.getCode().equals(code)) {
                return currencyEnum;
            }
        }
        return RMB;
    }
}
