package com.x.bp.core.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

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
     * 种类属性
     */
    private String prop;

    /**
     * 种类属性
     */
    private String propEn;

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

    /**
     * 排序
     */
    private Integer sort;
    /**
     * 服务
     */
    private String service;

    /**
     * 服务
     */
    private String serviceEn;

    /**
     * 最小购买数量
     */
    private Integer minNum;

    /**
     * 选项list
     */
    private List<PropDTO> propList;

    @Data
    public static class PropDTO {
        private String prop;
        private String propEn;
        private Long skuId;
        /**
         * 价格
         */
        private BigDecimal price;
    }
}
