package com.x.bp.core.vo.user;

import lombok.Data;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Data
public class UserLoginVO {
    /**
     * token
     */
    private String token;

    private Integer userType;
}
