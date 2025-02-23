package com.x.bp.core.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderVO {
    private Long id;

    private String orderNo;

    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    private Long platform;

    /**
     * 平台名称
     */
    private String platformName;

    /**
     * 平台名称
     */
    private String platformNameEn;

    private Integer orderStatus;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 下单币种
     */
    private String currency;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 子单列表
     */
    private List<OrderItemVO> orderItemVOList;
}
