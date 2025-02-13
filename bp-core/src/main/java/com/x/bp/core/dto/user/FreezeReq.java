package com.x.bp.core.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tianhe
 * @Description
 * @date 2023/12/1 6:50 PM
 */

@Data
public class FreezeReq {
	/**
	 * 用户id
	 */
	@ApiModelProperty(required = true)
	@NotNull(message = "用户id不能为空")
	private Long userId;

	/**
	 * 0解冻；1冻结
	 */
	@ApiModelProperty(required = true)
	@NotNull(message = "状态不能为空")
	private Integer status;

}
