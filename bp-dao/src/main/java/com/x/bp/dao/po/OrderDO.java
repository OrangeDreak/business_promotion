package com.x.bp.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 10:05
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "bp_order",autoResultMap = true)
public class OrderDO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orderNo;

    private Long userId;

    private Integer platform;

    private Integer orderStatus;

    private Long totalAmount;

    private String currency;

    private Long outTotalAmount;

    private String ext;

    private Integer isDelete;

    private Date gmtCreate;

    private Date gmtModified;

}
