package com.x.bp.core.vo.product;

import lombok.Data;

@Data
public class ProductVO {
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
     * 平台
     */
    private Long platform;

    /**
     * 排序
     */
    private Integer sort;
}
