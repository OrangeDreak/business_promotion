package com.x.bp.core.repository;

import com.x.bp.common.enums.OrderStatusEnum;
import com.x.bp.common.utils.TimeUtils;
import com.x.bp.core.dto.order.CreateOrderDTO;
import com.x.bp.core.service.product.ProductService;
import com.x.bp.dao.mapper.OrderMapper;
import com.x.bp.dao.po.OrderDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 10:03
 * @Description
 */
@Slf4j
@Component
public class OrderServiceRepository {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProductService productService;


    public CreateOrderDTO addOrderData(CreateOrderDTO createOrderDTO) {

        OrderDO orderDO = new OrderDO();
        orderDO.setOrderNo(createOrderDTO.getOrderNo());
        orderDO.setCurrency(createOrderDTO.getCurrency());
        orderDO.setUserId(createOrderDTO.getUserId());
        orderDO.setTotalAmount(createOrderDTO.getTotalAmount());
        orderDO.setPlatform(createOrderDTO.getPlatform());
        orderDO.setOrderStatus(OrderStatusEnum.WAIT_PAY.getStatus());
        orderDO.setGmtCreate(new Date());
        int insertResult = orderMapper.insert(orderDO);
        return insertResult > 0 ? createOrderDTO : null;
    }
}
