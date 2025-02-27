package com.x.bp.core.service.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.x.bp.common.constant.NumberConstants;
import com.x.bp.common.enums.CurrencyEnum;
import com.x.bp.common.enums.EnumError;
import com.x.bp.common.exception.CommonBizException;
import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.OrderNoUtils;
import com.x.bp.common.utils.Validator;
import com.x.bp.core.dto.cart.CartSkuDTO;
import com.x.bp.core.dto.order.CreateOrderBO;
import com.x.bp.core.dto.order.CreateOrderReq;
import com.x.bp.core.dto.order.CreateOrderDTO;
import com.x.bp.core.dto.order.OrderQueryReq;
import com.x.bp.core.dto.order.OrderStatusUpdateReq;
import com.x.bp.core.repository.OrderItemRepository;
import com.x.bp.core.repository.OrderServiceRepository;
import com.x.bp.core.repository.ProductSnapshotRepository;
import com.x.bp.core.service.product.PlatformService;
import com.x.bp.core.service.product.ProductService;
import com.x.bp.core.service.user.UserService;
import com.x.bp.core.utils.ExchangeUtil;
import com.x.bp.core.vo.order.OrderItemVO;
import com.x.bp.core.vo.order.OrderVO;
import com.x.bp.dao.po.OrderDO;
import com.x.bp.dao.po.OrderItemDO;
import com.x.bp.dao.po.PlatformDO;
import com.x.bp.dao.po.ProductDO;
import com.x.bp.dao.po.ProductSkuDO;
import com.x.bp.dao.po.ProductSnapshotDO;
import com.x.bp.dao.po.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    @Resource
    private UserService userService;
    @Resource
    private PlatformService platformService;

    public ServiceResultTO<CreateOrderDTO> createOrder(CreateOrderReq createOrderReq) {
        ServiceResultTO<List<CreateOrderBO>> dataCheckResult = dataCheckAndConvert(createOrderReq);
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setOrderIdList(new ArrayList<>());
        Long totalAmount = 0L;
        for (CreateOrderBO orderBO : dataCheckResult.getData()) {
            //添加到订单主表
            orderServiceRepository.insert(orderBO.getOrderDO());
            createOrderDTO.getOrderIdList().add(orderBO.getOrderDO().getId());
            totalAmount += orderBO.getOrderDO().getTotalAmount();
            //添加订单子表
            orderItemRepository.addOrderItem(orderBO.getOrderItemDOS(), orderBO.getOrderDO().getId());
            //条件商品快照表
            productSnapshotRepository.addProductSnapshot(orderBO.getOrderDO().getId());
        }
        createOrderDTO.setTotalAmount(ExchangeUtil.exchange(totalAmount));
        sendOrderEmail(createOrderDTO.getOrderIdList());
        return ServiceResultTO.buildSuccess(createOrderDTO);
    }

    private void sendOrderEmail(List<Long> orderIds) {

    }

    private ServiceResultTO<List<CreateOrderBO>> dataCheckAndConvert(CreateOrderReq createOrderReq) {
        List<Long> skuIds = createOrderReq.getSkuList().stream().map(CartSkuDTO::getSkuId).collect(Collectors.toList());
        List<ProductSkuDO> productSkuDOList = productService.listSkuBySkuIds(skuIds);
        if (Validator.isNullOrEmpty(productSkuDOList)) {
            throw new CommonBizException(EnumError.PARAMETER_ERROR);
        }
        Map<Long, ProductSkuDO> skuDOMap = productSkuDOList.stream().collect(Collectors.toMap(ProductSkuDO::getId, Function.identity(), (v1, v2) -> v1));
        List<Long> productIds = productSkuDOList.stream().map(ProductSkuDO::getProductId).collect(Collectors.toList());
        List<ProductDO> productDOS = productService.listProductByProductIds(productIds);
        Map<Long, ProductDO> productDOMap = productDOS.stream().collect(Collectors.toMap(ProductDO::getId, Function.identity()));
        Map<Long, List<CartSkuDTO>> skuPlatformGroup = new HashMap<>();
        createOrderReq.getSkuList().forEach(sku -> {
            ProductSkuDO productSkuDO = skuDOMap.get(sku.getSkuId());
            if (null == productSkuDO) {
                throw new CommonBizException(EnumError.PRODUCT_TAKEN_DOWN);
            }
            if (productSkuDO.getStock() <= sku.getNum()) {
                throw new CommonBizException(EnumError.PRODUCT_SOLD_OUT);
            }
            ProductDO productDO = productDOMap.get(productSkuDO.getProductId());
            if (null == productDO) {
                throw new CommonBizException(EnumError.PRODUCT_TAKEN_DOWN);
            }
            List<CartSkuDTO> platformSku = skuPlatformGroup.getOrDefault(productDO.getPlatform(), new ArrayList<>());
            platformSku.add(sku);
            skuPlatformGroup.put(productDO.getPlatform(), platformSku);
        });

        List<CreateOrderBO> orderBOS = new ArrayList<>();
        for (Map.Entry<Long, List<CartSkuDTO>> entry : skuPlatformGroup.entrySet()) {
            List<CartSkuDTO> skuDTOList = entry.getValue();
            CreateOrderBO orderBO = new CreateOrderBO();
            Long totalPrice = NumberConstants.NUMBER_LONG_ZERO;
            orderBO.setOrderItemDOS(new ArrayList<>());
            for (CartSkuDTO sku : skuDTOList) {
                ProductSkuDO skuDO = skuDOMap.get(sku.getSkuId());
                ProductDO productDO = productDOMap.get(skuDO.getProductId());
                OrderItemDO orderItemDO = buildOrderItem(sku, skuDO, productDO, createOrderReq.getUserId(), entry.getKey());
                totalPrice += orderItemDO.getSubtotal();
                orderBO.getOrderItemDOS().add(orderItemDO);
            }
            OrderDO orderDO = new OrderDO();
            orderDO.setOrderNo(OrderNoUtils.getCustomerOrderNo());
            orderDO.setUserId(createOrderReq.getUserId());
            orderDO.setPlatform(entry.getKey());
            orderDO.setTotalAmount(totalPrice);
            orderDO.setCurrency(CurrencyEnum.getByCode(createOrderReq.getCurrency()).getCurrency());
            orderBO.setOrderDO(orderDO);
            orderBOS.add(orderBO);
        }
        return ServiceResultTO.buildSuccess(orderBOS);
    }

    private OrderItemDO buildOrderItem(CartSkuDTO sku, ProductSkuDO skuDO, ProductDO productDO, Long userId, Long platform) {
        Long subTotal = skuDO.getPrice() * sku.getNum();
        OrderItemDO orderItemDO = new OrderItemDO();
        orderItemDO.setUserId(userId);
        orderItemDO.setPlatform(platform);
        orderItemDO.setProductId(productDO.getId());
        orderItemDO.setSkuId(skuDO.getId());
        orderItemDO.setProductName(productDO.getTitle());
        orderItemDO.setSkuCount(sku.getNum());
        orderItemDO.setSubtotal(subTotal);
        orderItemDO.setRealSubtotal(subTotal);
        return orderItemDO;
    }

    public ServicePageResult<OrderVO> list(OrderQueryReq req) {
        LambdaQueryWrapper<OrderDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDO::getIsDelete, 0);
        queryWrapper.eq(Validator.greaterZero(req.getPlatform()), OrderDO::getPlatform, req.getPlatform());
        queryWrapper.eq(Validator.greaterZero(req.getUserId()), OrderDO::getUserId, req.getUserId());
        queryWrapper.eq(null != req.getOrderStatus(), OrderDO::getOrderStatus, req.getOrderStatus());
        queryWrapper.eq(Validator.greaterZero(req.getOrderId()), OrderDO::getId, req.getOrderId());
        queryWrapper.eq(StringUtils.isNotBlank(req.getOrderNo()), OrderDO::getOrderNo, req.getOrderNo());
        queryWrapper.ge(null != req.getStartGmtCreateTime(), OrderDO::getGmtCreate, req.getStartGmtCreateTime());
        queryWrapper.le(null != req.getEndGmtCreateTime(), OrderDO::getGmtCreate, req.getEndGmtCreateTime());
        queryWrapper.orderByDesc(OrderDO::getId);
        if (StringUtils.isNotBlank(req.getUserKey())) {
            UserDO userDO = userService.getUserByLoginName(req.getUserKey(), 1);
            if (null == userDO) {
                return ServicePageResult.buildSuccess(Collections.emptyList(), 0);
            }
            queryWrapper.eq(OrderDO::getUserId, userDO.getId());
        }

        IPage<OrderDO> iPage = orderServiceRepository.selectPage(req.getPageNo(), req.getPageSize(), queryWrapper);
        if (null == iPage) {
            return ServicePageResult.buildSuccess(Collections.emptyList(), 0);
        }
        if (CollectionUtils.isEmpty(iPage.getRecords())) {
            return ServicePageResult.buildSuccess(Collections.emptyList(), iPage.getTotal());
        }
        List<OrderDO> orderDOS = iPage.getRecords();
        List<Long> orderIds = orderDOS.stream().map(OrderDO::getId).collect(Collectors.toList());
        List<Long> platformIds = orderDOS.stream().map(OrderDO::getPlatform).collect(Collectors.toList());
        List<Long> userIds = orderDOS.stream().map(OrderDO::getUserId).collect(Collectors.toList());
        List<OrderItemDO> orderItemDOS = orderItemRepository.getOrderItemListByOrderIds(orderIds);
        Map<Long, List<OrderItemDO>> orderItemGroup = orderItemDOS.stream().collect(Collectors.groupingBy(OrderItemDO::getOrderId));
        List<ProductSnapshotDO> productSnapshotDOS = productSnapshotRepository.listByOrderIds(orderIds);
        Map<Long, ProductSnapshotDO> productSnapshotDOMap = productSnapshotDOS.stream().collect(Collectors.toMap(ProductSnapshotDO::getSubOrderId, Function.identity()));
        List<PlatformDO> platformDOList = platformService.listByIds(platformIds);
        Map<Long, PlatformDO> platformDOMap = platformDOList.stream().collect(Collectors.toMap(PlatformDO::getId, Function.identity()));
        List<UserDO> userDOS = userService.listByIds(userIds);
        Map<Long, UserDO> userDOMap = userDOS.stream().collect(Collectors.toMap(UserDO::getId, Function.identity()));
        List<OrderVO> orderVOS = new ArrayList<>();
        orderDOS.forEach(order -> {
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            UserDO userDO = userDOMap.get(order.getUserId());
            orderVO.setUserName(null != userDO ? userDO.getNickname() : "");
            orderVO.setUserEmail(null != userDO ? userDO.getEmail() : "");
            PlatformDO platformDO = platformDOMap.get(order.getPlatform());
            if (null != platformDO) {
                orderVO.setPlatformName(platformDO.getName());
                orderVO.setPlatformNameEn(platformDO.getNameEn());
            }
            CurrencyEnum currencyEnum = CurrencyEnum.getByCurrency(order.getCurrency());
            orderVO.setTotalAmount(ExchangeUtil.exchange(order.getTotalAmount(), order.getGmtCreate(), currencyEnum.getCode()));
            orderVO.setOrderItemVOList(new ArrayList<>());
            orderVOS.add(orderVO);
            List<OrderItemDO> orderItemDOList = orderItemGroup.get(order.getId());
            if (CollectionUtils.isEmpty(orderItemDOList)) {
                return;
            }
            orderItemDOList.forEach(orderItem -> {
                OrderItemVO orderItemVO = new OrderItemVO();
                BeanUtils.copyProperties(orderItem, orderItemVO);
                ProductSnapshotDO snapshotDO = productSnapshotDOMap.get(orderItem.getId());
                BeanUtils.copyProperties(snapshotDO, orderItemVO);
                BeanUtils.copyProperties(orderItem, orderItemVO);
                orderItemVO.setPrice(ExchangeUtil.exchange(snapshotDO.getPrice(), order.getGmtCreate(), currencyEnum.getCode()));
                orderItemVO.setSubtotal(ExchangeUtil.exchange(orderItem.getSubtotal(), order.getGmtCreate(), currencyEnum.getCode()));
                orderVO.getOrderItemVOList().add(orderItemVO);
            });
        });
        return ServicePageResult.buildSuccess(orderVOS, iPage.getTotal());
    }

    public void updateStatus(OrderStatusUpdateReq req) {
        OrderDO orderDO = orderServiceRepository.getById(req.getId());
        if (null == orderDO) {
            throw new CommonBizException("订单不存在");
        }
        orderDO.setOrderStatus(req.getStatus());
        orderServiceRepository.updateById(orderDO);
    }
}

