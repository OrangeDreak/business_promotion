package com.x.bp.core.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSkuDTO {
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
     * 可售卖库存
     */
    private Integer stock;

    /**
     * 价格
     */
    private BigDecimal price;
}
