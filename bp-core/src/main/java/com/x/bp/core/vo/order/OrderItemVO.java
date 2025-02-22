package com.x.bp.core.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItemVO {
    private Long id;

    private Long productId;

    private Long skuId;

    /**
     * 数量
     */
    private Integer skuCount;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 总价
     */
    private BigDecimal subtotal;

    private Integer subOrderStatus;

    private String remark;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题
     */
    private String titleEn;

    /**
     * 规格属性
     */
    private String attributes;

    /**
     * 规格属性
     */
    private String attributesEn;

    /**
     * 种类属性
     */
    private String prop;

    /**
     * 种类属性
     */
    private String propEn;
}
