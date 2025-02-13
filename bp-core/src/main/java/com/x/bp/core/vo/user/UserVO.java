package com.x.bp.core.vo.user;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
     * 密码
     */
    private String password;

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
     * 注销原因
     */
    private String logoffReason;

    /**
     * 注销时间
     */
    private Date logoffTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /** 创建人id */
    private Long creatorId;

    /** 更新人id */
    private Long updaterId;

    /** 角色 */
    private List<String> roleIds;

    /** 角色 */
    private List<String> roleAlias;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 岗位名
     */
    private String postName;

    /**
     * 性别
     */
    private String sexName;

    /**
     * 添加人
     */
    private String createUsername;
}
