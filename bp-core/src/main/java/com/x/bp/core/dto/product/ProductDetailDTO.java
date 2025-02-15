package com.x.bp.core.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDTO extends ProductDTO {
    List<ProductSkuDTO> skuDTOList;
}
