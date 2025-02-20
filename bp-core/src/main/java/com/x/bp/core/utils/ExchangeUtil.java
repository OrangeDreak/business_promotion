package com.x.bp.core.utils;

import cn.hutool.core.util.NumberUtil;
import com.x.bp.common.enums.CurrencyEnum;
import com.x.bp.common.utils.ApiContextUtil;
import com.x.bp.common.utils.Validator;
import com.x.bp.core.service.product.ExchangeService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Component
public class ExchangeUtil implements ApplicationContextAware {
    private static ExchangeService exchangeService;

    private ExchangeUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        exchangeService = (ExchangeService)applicationContext.getBean("exchangeService");
    }


    public static BigDecimal exchange(Long price) {
        return exchange(price, new Date());
    }

    public static BigDecimal exchange(Long price, Date date) {
        Integer currency = ApiContextUtil.getCurrency();
        return exchange(price, date, currency);
    }

    public static BigDecimal exchange(Long price, Date date, Integer currency) {
        if (!Validator.greaterZero(price)) {
            return BigDecimal.ZERO;
        }
        BigDecimal rate = exchangeService.getExchangeRate(CurrencyEnum.getByCode(currency), date);

        return NumberUtil.mul(BigDecimal.valueOf(price), rate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }

}
