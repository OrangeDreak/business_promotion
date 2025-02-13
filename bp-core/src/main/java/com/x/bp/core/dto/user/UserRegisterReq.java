package com.x.bp.core.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Data
public class UserRegisterReq implements Serializable {
    /**
     * 国家/地区
     */
    @NotNull(message = "国家/地区不能为空")
    @ApiModelProperty(required = true)
    private Long areaId;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(required = true)
    @Email
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(required = true)
    private String password;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 邮箱验证码
     */
    @NotBlank(message = "邮箱验证码不能为空")
    @ApiModelProperty(required = true)
    private String emailCaptcha;

    /**
     * 图形验证码key
     */
    @NotBlank(message = "图形验证码key不能为空")
    @ApiModelProperty(required = true)
    private String captchaKey;

    /**
     * 图形验证码
     */
    @NotBlank(message = "图形验证码不能为空")
    @ApiModelProperty(required = true)
    private String captchaCode;


}
