package com.x.bp.core.dto.order;

import com.x.bp.dao.query.base.BaseQuery;
import lombok.Data;

import java.util.Date;

@Data
public class OrderQueryReq extends BaseQuery {

    /**
     * 平台
     */
    private Long platform;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单状态
     */
    private Integer orderStatus;


    private Long orderId;

    /**
     * 订单号查询
     */
    private String orderNo;

    /**
     * 创建时间-开始
     */
    private Date startGmtCreateTime;

    /**
     * 创建时间-结束
     */
    private Date endGmtCreateTime;
}
