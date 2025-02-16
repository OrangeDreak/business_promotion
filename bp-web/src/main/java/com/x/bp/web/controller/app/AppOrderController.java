package com.x.bp.web.controller.app;

import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.utils.ApiContextUtil;
import com.x.bp.core.dto.order.OrderQueryReq;
import com.x.bp.core.service.order.OrderService;
import com.x.bp.core.vo.order.OrderVO;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.ApiContextUtil;
import com.x.bp.core.common.Result;
import com.x.bp.core.dto.order.CreateOrderDTO;
import com.x.bp.core.dto.order.CreateOrderReq;
import com.x.bp.core.dto.user.UserLoginReq;
import com.x.bp.core.service.order.OrderService;
import com.x.bp.web.annotion.LoginNotRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zouzhe
 * @CreateDate 2025/2/15 17:24
 * @Description
 */
@RestController
@RequestMapping("/app/order")
@Api(value = "订单")
public class AppOrderController {
    @Resource
    private OrderService orderService;
    @ApiOperation(value = "订单列表")
    @PostMapping("/myOrderList")
    public ServicePageResult<OrderVO> myOrderList(@RequestBody @Validated OrderQueryReq req) {
        req.setUserId(ApiContextUtil.getUserId());
        return orderService.list(req);
    }

    @Resource
    private OrderService orderService;

    @ApiOperation("创建订单")
    @PostMapping("/createOrder")
    public Result<CreateOrderDTO> createOrder(@RequestBody @Validated CreateOrderReq req) {
        req.setUserId(ApiContextUtil.getUserId());
        ServiceResultTO<CreateOrderDTO> createOrderResult = orderService.createOrder(req);
        if (createOrderResult.isSuccess()) {
            return Result.buildSuccess(createOrderResult.getData());
        }
        return Result.buildFail(createOrderResult.getMessage());
    }
}
