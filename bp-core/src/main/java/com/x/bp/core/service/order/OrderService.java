package com.x.bp.core.service.order;

import com.x.bp.common.constant.NumberConstants;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.OrderNoUtils;
import com.x.bp.common.utils.Validator;
import com.x.bp.core.dto.cart.CartSkuDTO;
import com.x.bp.core.dto.order.CreateOrderReq;
import com.x.bp.core.dto.order.CreateOrderDTO;
import com.x.bp.core.repository.OrderItemRepository;
import com.x.bp.core.repository.OrderServiceRepository;
import com.x.bp.core.repository.ProductSnapshotRepository;
import com.x.bp.core.service.product.ProductService;
import com.x.bp.dao.po.OrderItemDO;
import com.x.bp.dao.po.ProductSkuDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 10:03
 * @Description
 */
@Slf4j
@Component
public class OrderService {

    @Resource
    private OrderServiceRepository orderServiceRepository;

    @Resource
    private OrderItemRepository orderItemRepository;

    @Resource
    private ProductSnapshotRepository productSnapshotRepository;

    @Resource
    private ProductService productService;

    public ServiceResultTO<CreateOrderDTO> createOrder(CreateOrderReq createOrderReq) {
        ServiceResultTO<CreateOrderDTO> dataCheckResult = dataCheck(createOrderReq);
        if (!dataCheckResult.isSuccess()) {
            return dataCheckResult;
        }
        CreateOrderDTO createOrderDTO = dataCheckResult.getData();
        try {
            //添加到订单主表
            CreateOrderDTO orderDTO = orderServiceRepository.addOrderData(createOrderDTO);
            if (Validator.isNullOrEmpty(orderDTO)) {
                return ServiceResultTO.buildFailed("订单创建失败");
            }
            createOrderDTO.setOrderId(orderDTO.getOrderId());
            //添加订单子表
            orderItemRepository.addOrderItem(createOrderDTO);
            //条件商品快照表
            productSnapshotRepository.addProductSnapshot(orderDTO.getOrderId());

        } catch (Exception e) {
            log.error("订单创建失败", e);
        }
        return ServiceResultTO.buildSuccess(createOrderDTO);
    }

    private ServiceResultTO<CreateOrderDTO> dataCheck(CreateOrderReq createOrderReq) {
        if (Validator.isNotId(createOrderReq.getUserId())) {
            return ServiceResultTO.buildFailed("用户id不能为空");
        }
        List<Long> skuIds = createOrderReq.getSkuList().stream().map(CartSkuDTO::getSkuId).collect(Collectors.toList());
        List<ProductSkuDO> productSkuDOList = productService.listSkuBySkuIds(skuIds);
        if (Validator.isNullOrEmpty(productSkuDOList)) {
            return ServiceResultTO.buildFailed("商品sku不能为空");
        }
        List<ProductSkuDO> skuOutOfStock = productSkuDOList.stream().filter(productSkuDO -> productSkuDO.getStock() == 0).collect(Collectors.toList());
        if (Validator.isNotNullOrEmpty(skuOutOfStock)) {
        }
        Map<Long, ProductSkuDO> skuDOMap = skuOutOfStock.stream().collect(Collectors.toMap(ProductSkuDO::getId, Function.identity(), (v1, v2) -> v1));
        Long totalPrice = NumberConstants.NUMBER_LONG_ZERO;
        for (CartSkuDTO cartSkuDTO : createOrderReq.getSkuList()) {
            ProductSkuDO productSkuDO = skuDOMap.get(cartSkuDTO.getSkuId());
            if (productSkuDO.getStock() <= NumberConstants.NUMBER_ZERO) {
                return ServiceResultTO.buildFailed("商品sku库存不足");
            }
            if (productSkuDO.getStock() < cartSkuDTO.getNum()) {
                return ServiceResultTO.buildFailed("购买的商品数量超过了商品库存");
            }
            totalPrice += productSkuDO.getPrice() * cartSkuDTO.getNum();
        }

        return ServiceResultTO.buildSuccess(buildOrderCreateDTO(createOrderReq, totalPrice));
    }

    private CreateOrderDTO buildOrderCreateDTO(CreateOrderReq createOrderReq, Long totalPrice) {
        CreateOrderDTO orderCreateDTO = new CreateOrderDTO();
        String customerOrderNo = OrderNoUtils.getCustomerOrderNo();
        orderCreateDTO.setOrderNo(customerOrderNo);
        orderCreateDTO.setUserId(createOrderReq.getUserId());
        orderCreateDTO.setPlatform(createOrderReq.getPlatform());
        orderCreateDTO.setCurrency(createOrderReq.getCurrency());
        orderCreateDTO.setSkuList(createOrderReq.getSkuList());
        orderCreateDTO.setTotalAmount(totalPrice);

        return orderCreateDTO;

    }
}

