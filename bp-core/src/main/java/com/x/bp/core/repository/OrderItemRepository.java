package com.x.bp.core.repository;

import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.bp.core.dto.cart.CartSkuDTO;
import com.x.bp.core.dto.order.CreateOrderDTO;
import com.x.bp.core.vo.order.OrderItemVO;
import com.x.bp.dao.mapper.OrderItemMapper;
import com.x.bp.dao.po.OrderItemDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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

    public void addOrderItem(List<OrderItemDO> itemDOS, Long orderId) {
        if (CollectionUtils.isEmpty(itemDOS)) {
            return;
        }
        itemDOS.forEach(itemDO -> {
            itemDO.setOrderId(orderId);
            orderItemMapper.insert(itemDO);
        });
    }

    public List<OrderItemDO> getOrderItemListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderItemDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderItemDO::getOrderId, orderId);
        lambdaQueryWrapper.eq(OrderItemDO::getIsDelete, 0);
        return orderItemMapper.selectList(lambdaQueryWrapper);
    }

    public List<OrderItemDO> getOrderItemListByOrderIds(List<Long> orderIds) {
        if (CollectionUtils.isEmpty(orderIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<OrderItemDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(OrderItemDO::getOrderId, orderIds);
        lambdaQueryWrapper.eq(OrderItemDO::getIsDelete, 0);
        return orderItemMapper.selectList(lambdaQueryWrapper);
    }
}
