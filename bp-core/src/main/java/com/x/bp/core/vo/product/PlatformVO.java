package com.x.bp.core.vo.product;

import lombok.Data;

@Data
public class PlatformVO {
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 名称
     */
    private String nameEn;

    /**
     * 标签
     */
    private String label;
}
