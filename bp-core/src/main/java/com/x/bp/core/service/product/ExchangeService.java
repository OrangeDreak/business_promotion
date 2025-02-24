package com.x.bp.core.service.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.bp.common.enums.CurrencyEnum;
import com.x.bp.common.enums.EnumError;
import com.x.bp.common.exception.CommonBizException;
import com.x.bp.dao.mapper.ExchangeRateMapper;
import com.x.bp.dao.po.ExchangeRateDO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @desc ExchangeService
 **/
@Component
public class ExchangeService {

    @Resource
    private ExchangeRateMapper exchangeRateMapper;
    /**
     * 获取汇率 ==  当前时间
     * @param currencyEnum
     * @return
     */
    public BigDecimal getExchangeRate(CurrencyEnum currencyEnum){
        return getExchangeRate(currencyEnum, new Date());
    }

    public BigDecimal getExchangeRate(CurrencyEnum currencyEnum, Date date){
        if(currencyEnum.getCode().equals(CurrencyEnum.USD.getCode())){
            return BigDecimal.ONE;
        }
        ExchangeRateDO exchangeRateDO = getCurrencyRate(currencyEnum.getCode(), date);
        if(exchangeRateDO == null){
            throw new CommonBizException(EnumError.EXCHANGE_RATE_GET_FAIL);
        }
        return exchangeRateDO.getExchangeRate();
    }


    /**
     * 获取货币汇率
     * @param currency 货币
     * @param date 在日期之前
     * @return
     */
    public ExchangeRateDO getCurrencyRate(Integer currency, Date date){
        LambdaQueryWrapper<ExchangeRateDO> qw = new LambdaQueryWrapper<>();
        qw.eq(ExchangeRateDO::getIsDelete, 0)
            .le(ExchangeRateDO::getGmtCreate, date)
            .eq(ExchangeRateDO::getExchangeRateType, currency)
            .orderByDesc(ExchangeRateDO::getGmtCreate)
            .last("limit 1")
        ;
        return exchangeRateMapper.selectOne(qw);
    }
}
