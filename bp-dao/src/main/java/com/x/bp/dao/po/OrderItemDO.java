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
@TableName(value = "bp_order_item",autoResultMap = true)
public class OrderItemDO {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long platform;

    private Long orderId;

    private Long productId;

    private Long skuId;

    private String productName;

    private Integer skuCount;

    private Long subtotal;

    private Long realSubtotal;

    private Integer subOrderStatus;

    private String remark;

    private Integer isDelete;

    private Date gmtCreate;

    private Date gmtModified;

}
