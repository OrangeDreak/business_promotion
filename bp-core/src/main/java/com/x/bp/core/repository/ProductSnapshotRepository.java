package com.x.bp.core.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.Validator;
import com.x.bp.core.service.product.ProductService;
import com.x.bp.dao.mapper.ProductSnapshotMapper;
import com.x.bp.dao.po.OrderItemDO;
import com.x.bp.dao.po.ProductDO;
import com.x.bp.dao.po.ProductSkuDO;
import com.x.bp.dao.po.ProductSnapshotDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:01
 * @Description
 */
@Slf4j
@Component
public class ProductSnapshotRepository {

    @Resource
    private ProductSnapshotMapper productSnapshotMapper;

    @Resource
    private OrderItemRepository orderItemRepository;

    @Resource
    private ProductService productService;

    public ServiceResultTO addProductSnapshot(Long orderId) {

        List<OrderItemDO> orderItemListByOrderId = orderItemRepository.getOrderItemListByOrderId(orderId);
        if (Validator.isNullOrEmpty(orderItemListByOrderId)) {
            return ServiceResultTO.buildFailed("订单项不能为空");
        }
        List<Long> productIds = orderItemListByOrderId.stream().map(OrderItemDO::getProductId).collect(Collectors.toList());
        List<Long> skuIds = orderItemListByOrderId.stream().map(OrderItemDO::getSkuId).collect(Collectors.toList());
        List<ProductDO> productDOS = productService.listProductByProductIds(productIds);
        List<ProductSkuDO> productSkuDOList = productService.listSkuBySkuIds(skuIds);
        if (Validator.isNullOrEmpty(productDOS)) {
            return ServiceResultTO.buildFailed("商品不能为空");
        }
        if (Validator.isNullOrEmpty(productSkuDOList)) {
            return ServiceResultTO.buildFailed("商品sku不能为空");
        }
        Map<Long, ProductDO> productDOMap = productDOS.stream().collect(Collectors.toMap(ProductDO::getId, Function.identity(), (v1, v2) -> v1));
        Map<Long, ProductSkuDO> skuDOMap = productSkuDOList.stream().collect(Collectors.toMap(ProductSkuDO::getId, Function.identity(), (v1, v2) -> v1));
        orderItemListByOrderId.forEach(orderItemDO -> {
            ProductDO productDO = productDOMap.get(orderItemDO.getProductId());
            ProductSkuDO productSkuDO = skuDOMap.get(orderItemDO.getSkuId());
            ProductSnapshotDO productSnapshotDO = ProductSnapshotDO.builder()
                    .orderId(orderId)
                    .productId(orderItemDO.getProductId())
                    .skuId(orderItemDO.getSkuId())
                    .skuCount(orderItemDO.getSkuCount())
                    .price(orderItemDO.getSubtotal())
                    .title(productDO.getTitle())
                    .titleEn(productDO.getTitleEn())
                    .attributes(productSkuDO.getAttributes())
                    .attributesEn(productSkuDO.getAttributesEn())
                    .prop(productSkuDO.getProp())
                    .propEn(productSkuDO.getPropEn())
                    .gmtCreate(new Date())
                    .build();
            productSnapshotMapper.insert(productSnapshotDO);
        });

        return ServiceResultTO.buildSuccess(null,"商品快照表新增成功");
    }

    public List<ProductSnapshotDO> listByOrderIds(List<Long> orderIds) {
        if (CollectionUtils.isEmpty(orderIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ProductSnapshotDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(ProductSnapshotDO::getOrderId, orderIds);
        lambdaQueryWrapper.eq(ProductSnapshotDO::getIsDelete, 0);
        return productSnapshotMapper.selectList(lambdaQueryWrapper);
    }
}
