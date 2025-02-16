package com.x.bp.core.dto.order;

import com.x.bp.core.dto.cart.CartSkuDTO;
import lombok.Data;

import java.util.List;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:32
 * @Description
 */
@Data
public class CreateOrderDTO {
    private String orderNo;
    private Long orderId;
    private Long userId;
    private Long totalAmount;
    private Integer platform;
    private String currency;
    private List<CartSkuDTO> skuList;
}
