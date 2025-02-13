package com.x.bp.core.dto.user;

import com.x.bp.core.enums.UserSexEnum;
import lombok.Data;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Data
public class UpdateUserInfoReq {
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别
     * @see UserSexEnum
     */
    private Integer sex;

}
