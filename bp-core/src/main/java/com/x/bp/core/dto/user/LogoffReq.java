package com.x.bp.core.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Data
public class LogoffReq {
    /**
     * 原因
     */
    @NotBlank(message = "原因不能为空")
    @ApiModelProperty(required = true)
    private String reason;
}
