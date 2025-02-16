package com.x.bp.core.repository;

import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.utils.OrderNoUtils;
import com.x.bp.core.dto.order.CreateOrderReq;
import com.x.bp.core.dto.order.OrderDetailDTO;
import com.x.bp.core.service.product.ProductService;
import com.x.bp.dao.mapper.OrderMapper;
import com.x.bp.dao.po.OrderDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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


    public Boolean addOrderData(CreateOrderReq createOrderReq) {

        OrderDO orderDO = new OrderDO();

        orderMapper.insert(orderDO);
        return true;
    }
}
