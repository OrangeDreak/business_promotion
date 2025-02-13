package com.x.bp.core.dto.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CountryDTO {
    @ApiModelProperty(value = "国家名称")
    private String name;
    @ApiModelProperty(value = "国家二字码")
    private String code;
}
