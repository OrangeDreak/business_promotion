package com.x.bp.core.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author: zhaofs
 * @date: 2024/09/30
 */
@Data
public class AddUserReq {
    /**
     * 工号
     */
    private String jobNo;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 备注
     */
    private String remark;

    /**
     * 备注
     */
    @ApiModelProperty(value = "角色ID 英文逗号拼接")
    private String roleId;

    /**
     * 备注
     */
    @ApiModelProperty(value = "部门ID 英文逗号拼接")
    private String deptId;
}
