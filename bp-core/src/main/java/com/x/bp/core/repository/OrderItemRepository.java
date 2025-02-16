package com.x.bp.core.repository;

import com.x.bp.dao.mapper.OrderItemMapper;
import com.x.bp.dao.po.OrderItemDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    public Boolean addOrderItem(OrderItemDO orderItemDO) {
        orderItemMapper.insert(orderItemDO);
        return true;
    }
}
