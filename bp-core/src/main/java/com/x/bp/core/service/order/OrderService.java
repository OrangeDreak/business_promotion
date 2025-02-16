package com.x.bp.core.service.order;

import com.x.bp.common.model.ServicePageResult;
import com.x.bp.core.dto.order.OrderDetailDTO;
import com.x.bp.core.dto.product.ProductDetailDTO;
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
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    public ServicePageResult<OrderDetailDTO> createOrder() {
        //生成订单号
        try {
            //添加到订单主表

        } catch (Exception e) {

        }
    }

    public Boolean addOrderData(OrderDO orderDO) {
        orderMapper.insert(orderDO);
        return true;
    }
}
