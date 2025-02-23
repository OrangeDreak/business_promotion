package com.x.bp.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    public void insert(OrderDO orderDO) {
        orderMapper.insert(orderDO);
    }

    public IPage<OrderDO> selectPage(Integer page, Integer pageSize, LambdaQueryWrapper<OrderDO> queryWrapper) {
        return orderMapper.selectPage(new Page<>(page, pageSize), queryWrapper);
    }
}
