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
     * 姓
     */
    @ApiModelProperty(required = true)
    private String firstName;

    /**
     * 名
     */
    @ApiModelProperty(required = true)
    private String lastName;

    /**
     * 邮箱
     */
    @NotBlank(message = "Email cannot be empty")
    @ApiModelProperty(required = true)
    @Email
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "Password cannot be empty")
    @ApiModelProperty(required = true)
    private String password;


}
