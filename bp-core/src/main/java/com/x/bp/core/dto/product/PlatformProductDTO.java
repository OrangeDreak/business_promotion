package com.x.bp.core.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class PlatformProductDTO {
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

    /**
     * 商品list
     */
    private List<ProductDTO> productDTOList;

}
