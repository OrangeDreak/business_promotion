package com.x.bp.core.dto.message;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MessageDTO {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 名称
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 主题
     */
    private String title;


    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}
