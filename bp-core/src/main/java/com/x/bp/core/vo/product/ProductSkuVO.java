package com.x.bp.core.vo.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSkuVO {
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 规格属性
     */
    private String attributes;

    /**
     * 规格属性
     */
    private String attributesEn;

    /**
     * 图片
     */
    private String image;

    /**
     * 总库存
     */
    private Integer total;

    /**
     * 已售卖库存
     */
    private BigDecimal price;

    /**
     * 排序
     */
    private Integer sort;
}
