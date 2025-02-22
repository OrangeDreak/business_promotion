package com.x.bp.core.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Data
public class UserVO implements Serializable {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 加密手机号
     */
    private String encryptedMobile;

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
     * 0正常；1冻结；2待激活；3已注销
     */
    private Integer status;

    /**
     * 用户类型 0：C端用户 1：后台用户
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}
