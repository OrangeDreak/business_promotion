package com.x.bp.core.dto.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题
     */
    private String titleEn;

    /**
     * 描述
     */
    private String desc;

    /**
     * 图片
     */
    private String image;

    /**
     * 1上架；0下架
     */
    private Integer status;
    /**
     * 价格
     */
    private BigDecimal price;

    private String platformName;

    private String platformNameEn;
}
