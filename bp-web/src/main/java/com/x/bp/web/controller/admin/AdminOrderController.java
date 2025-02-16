package com.x.bp.web.controller.admin;

import com.x.bp.common.model.ServicePageResult;
import com.x.bp.core.dto.order.OrderQueryReq;
import com.x.bp.core.service.order.OrderService;
import com.x.bp.core.vo.order.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 订单管理
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@RestController
@RequestMapping("/admin/order")
@Api(value = "订单管理")
public class AdminOrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation(value = "订单列表")
    @PostMapping("/list")
    public ServicePageResult<OrderVO> list(@RequestBody @Validated OrderQueryReq req) {
        return orderService.list(req);
    }

}
