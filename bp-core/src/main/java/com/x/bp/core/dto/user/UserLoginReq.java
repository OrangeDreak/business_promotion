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
public class UserLoginReq {
    /**
     * 登录名：手机号/工号/邮箱
     */
    @NotBlank(message = "登录名不能为空")
    @ApiModelProperty(required = true)
    private String loginName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(required = true)
    private String password;
}
