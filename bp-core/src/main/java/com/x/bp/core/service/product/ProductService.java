package com.x.bp.core.service.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.x.bp.common.enums.EnumError;
import com.x.bp.common.exception.CommonBizException;
import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.utils.Validator;
import com.x.bp.core.dto.product.PlatformProductDTO;
import com.x.bp.core.dto.product.ProductDTO;
import com.x.bp.core.dto.product.ProductDetailDTO;
import com.x.bp.core.dto.product.ProductSkuDTO;
import com.x.bp.dao.mapper.ProductMapper;
import com.x.bp.dao.mapper.ProductSkuMapper;
import com.x.bp.dao.po.PlatformDO;
import com.x.bp.dao.po.ProductDO;
import com.x.bp.dao.po.ProductSkuDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private PlatformService platformService;

    public ServicePageResult<PlatformProductDTO> listAllPlatformProduct() {
        List<PlatformDO> platformDOList = platformService.listValidPlatformBySort();
        if (CollectionUtils.isEmpty(platformDOList)) {
            return ServicePageResult.buildSuccess();
        }
        List<Long> platformIds = platformDOList.stream().map(PlatformDO::getId).collect(Collectors.toList());
        List<ProductDO> productDOS = listProductByPlatform(platformIds);
        Map<Long, List<ProductDO>> productPlatformMap = productDOS.stream().collect(Collectors.groupingBy(ProductDO::getPlatform));
        List<PlatformProductDTO> resultList = new ArrayList<>();
        platformDOList.forEach(platform -> {
            PlatformProductDTO platformProductDTO = new PlatformProductDTO();
            BeanUtils.copyProperties(platform, platformProductDTO);
            platformProductDTO.setProductDTOList(new ArrayList<>());
            List<ProductDO> products = productPlatformMap.get(platform.getId());
            resultList.add(platformProductDTO);
            if (CollectionUtils.isEmpty(products)) {
                return;
            }
            products = products.stream().sorted(Comparator.comparing(ProductDO::getSort)).collect(Collectors.toList());
            products.forEach(product -> {
                ProductDTO productDTO = new ProductDTO();
                BeanUtils.copyProperties(product, productDTO);
                productDTO.setPlatformName(platform.getName());
                productDTO.setPlatformNameEn(platform.getNameEn());
                platformProductDTO.getProductDTOList().add(productDTO);
            });
        });

        return ServicePageResult.buildSuccess(resultList);
    }


    public List<ProductDO> listProductByPlatform(List<Long> platformIds) {
        if (CollectionUtils.isEmpty(platformIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDO::getStatus, 1);
        queryWrapper.eq(ProductDO::getIsDelete, 0);
        queryWrapper.in(ProductDO::getPlatform, platformIds);
        queryWrapper.orderByDesc(ProductDO::getId);
        List<ProductDO> productDOS = productMapper.selectList(queryWrapper);
        return Validator.value(productDOS);
    }


    public ServicePageResult<ProductDetailDTO> listPlatformProduct(Long platform) {
        PlatformDO platformDO = platformService.getValidPlatformById(platform);
        if (null == platformDO) {
            throw new CommonBizException(EnumError.PLATFORM_INVALID);
        }
        List<ProductDO> productDOS = listProductByPlatform(Collections.singletonList(platformDO.getId()));
        if (CollectionUtils.isEmpty(productDOS)) {
            return ServicePageResult.buildSuccess();
        }
        productDOS = productDOS.stream().sorted(Comparator.comparing(ProductDO::getSort)).collect(Collectors.toList());
        List<Long> productIds = productDOS.stream().map(ProductDO::getId).collect(Collectors.toList());
        List<ProductSkuDO> skuDOList = listSkuByProductIds(productIds);
        Map<Long, List<ProductSkuDO>> productSkuMap = skuDOList.stream().collect(Collectors.groupingBy(ProductSkuDO::getProductId));

        List<ProductDetailDTO> resultList = new ArrayList<>();
        productDOS.forEach(product -> {
            ProductDetailDTO detailDTO = new ProductDetailDTO();
            BeanUtils.copyProperties(product, detailDTO);
            detailDTO.setSkuDTOList(new ArrayList<>());
            detailDTO.setPlatformName(platformDO.getName());
            detailDTO.setPlatformNameEn(platformDO.getNameEn());
            resultList.add(detailDTO);
            List<ProductSkuDO> skuDOS = productSkuMap.get(product.getId());
            if (CollectionUtils.isEmpty(skuDOS)) {
                return;
            }
            skuDOS = skuDOS.stream().sorted(Comparator.comparing(ProductSkuDO::getSort)).collect(Collectors.toList());
            skuDOS.forEach(sku -> {
                ProductSkuDTO skuDTO = new ProductSkuDTO();
                BeanUtils.copyProperties(sku, skuDTO);
                detailDTO.getSkuDTOList().add(skuDTO);
            });
        });
        return ServicePageResult.buildSuccess(resultList);
    }

    public List<ProductSkuDO> listSkuByProductIds(List<Long> productIds) {
        if (CollectionUtils.isEmpty(productIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ProductSkuDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProductSkuDO::getIsDelete, 0);
        lambdaQueryWrapper.in(ProductSkuDO::getProductId, productIds);
        lambdaQueryWrapper.orderByDesc(ProductSkuDO::getId);
        List<ProductSkuDO> productSkuDOS = productSkuMapper.selectList(lambdaQueryWrapper);
        return Validator.value(productSkuDOS);
    }
}
