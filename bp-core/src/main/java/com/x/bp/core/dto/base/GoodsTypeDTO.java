package com.x.bp.core.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GoodsTypeDTO {
    @ApiModelProperty(value = "商品类型MAP")
    private Map<String, List<GoodType>> goodsTypeMap;

    @Data
    public static class GoodType {
        @ApiModelProperty(value = "类型")
        private Integer type;
        @ApiModelProperty(value = "类型描述")
        private String desc;
    }
}
