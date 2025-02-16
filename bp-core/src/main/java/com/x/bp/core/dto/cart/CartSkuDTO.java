package com.x.bp.core.dto.cart;

import lombok.Data;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 16:15
 * @Description
 */
@Data
public class CartSkuDTO {
    private Long skuId;
    private Integer num;
    private Long productId;
    private String title;
    private String titleEn;
    private String attributes;
    private String attributesEn;
    private Long subtotal;
}
