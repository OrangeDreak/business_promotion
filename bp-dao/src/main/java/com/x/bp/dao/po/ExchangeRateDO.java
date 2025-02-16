package com.x.bp.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "bp_exchange_rate",autoResultMap = true)
public class ExchangeRateDO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 汇率 1外币=xRMB
     */
    private BigDecimal exchangeRate;

    /**
     * 外币类型 1美元
     */
    private Integer exchangeRateType;

    /**
     * 日期
     */
    private String exchangeRateDate;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}