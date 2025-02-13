package com.x.bp.core.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserRoleGrantReq {
    @ApiModelProperty(value = "角色ID合集")
    private List<Long> roleIds;

    /**
     * 备注
     */
    @ApiModelProperty(value = "用户ID合集")
    private List<Long> userIds;
}
