package com.x.bp.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.bp.core.dto.cart.CartSkuDTO;
import com.x.bp.core.dto.order.CreateOrderDTO;
import com.x.bp.dao.mapper.OrderItemMapper;
import com.x.bp.dao.po.OrderItemDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:00
 * @Description
 */
@Slf4j
@Component
public class OrderItemRepository {

    @Resource
    private OrderItemMapper orderItemMapper;

    public Boolean addOrderItem(CreateOrderDTO createOrderDTO) {
        List<CartSkuDTO> skuList = createOrderDTO.getSkuList();
        skuList.forEach(cartSkuDTO -> {
            OrderItemDO orderItemDO = OrderItemDO.builder()
                    .orderId(createOrderDTO.getOrderId())
                    .productId(cartSkuDTO.getProductId())
                    .skuId(cartSkuDTO.getSkuId())
                    .skuCount(cartSkuDTO.getNum())
                    .subtotal(cartSkuDTO.getSubtotal()).build();
            orderItemMapper.insert(orderItemDO);
        });

        return true;
    }

    public List<OrderItemDO> getOrderItemListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItemDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderItemDO::getOrderId, orderId);
        lambdaQueryWrapper.eq(OrderItemDO::getIsDelete, 0);
        return orderItemMapper.selectList(lambdaQueryWrapper);
    }
}
