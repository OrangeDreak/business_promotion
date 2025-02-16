package com.x.bp.core.service.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.x.bp.common.constant.NumberConstants;
import com.x.bp.common.enums.CurrencyEnum;
import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.OrderNoUtils;
import com.x.bp.common.utils.Validator;
import com.x.bp.core.dto.cart.CartSkuDTO;
import com.x.bp.core.dto.order.CreateOrderReq;
import com.x.bp.core.dto.order.CreateOrderDTO;
import com.x.bp.core.dto.order.OrderQueryReq;
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
        IPage<OrderDO> iPage = orderServiceRepository.selectPage(req.getPageNo(), req.getPageSize(), queryWrapper);
        if (null == iPage) {
            return ServicePageResult.buildSuccess(Collections.emptyList(), 0);
        }
        if (CollectionUtils.isEmpty(iPage.getRecords())) {
            return ServicePageResult.buildSuccess(Collections.emptyList(), iPage.getTotal());
        }
        List<OrderDO> orderDOS = iPage.getRecords();
        List<Long> orderIds = orderDOS.stream().map(OrderDO::getId).collect(Collectors.toList());
        List<Long> platformIds = orderDOS.stream().map(v -> Long.valueOf(v.getPlatform())).collect(Collectors.toList());
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
            orderVO.setUserName(null != userDO ? userDO.getNickName() : "");
            PlatformDO platformDO = platformDOMap.get(order.getPlatform().longValue());
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
                orderItemVO.setPrice(ExchangeUtil.exchange(snapshotDO.getPrice(), order.getGmtCreate(), currencyEnum.getCode()));
                orderItemVO.setSubtotal(ExchangeUtil.exchange(orderItem.getSubtotal(), order.getGmtCreate(), currencyEnum.getCode()));
                orderItemVO.setTitle(snapshotDO.getTitle());
                orderItemVO.setTitleEn(snapshotDO.getTitleEn());
                orderItemVO.setAttributes(snapshotDO.getAttributes());
                orderItemVO.setAttributesEn(snapshotDO.getAttributesEn());
                orderVO.getOrderItemVOList().add(orderItemVO);
            });
        });
        return ServicePageResult.buildSuccess(orderVOS, iPage.getTotal());
    }
}

