package com.x.bp.core.service.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.x.bp.common.enums.EnumError;
import com.x.bp.common.enums.ProductStatusEnum;
import com.x.bp.common.exception.CommonBizException;
import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.utils.PriceUtil;
import com.x.bp.common.utils.Validator;
import com.x.bp.core.dto.product.PlatformProductDTO;
import com.x.bp.core.dto.product.ProductDTO;
import com.x.bp.core.dto.product.ProductDetailDTO;
import com.x.bp.core.dto.product.ProductSkuDTO;
import com.x.bp.core.dto.user.ProductQueryReq;
import com.x.bp.core.utils.ExchangeUtil;
import com.x.bp.core.vo.product.ProductSkuVO;
import com.x.bp.core.vo.product.ProductVO;
import com.x.bp.dao.mapper.ProductMapper;
import com.x.bp.dao.mapper.ProductSkuMapper;
import com.x.bp.dao.po.PlatformDO;
import com.x.bp.dao.po.ProductDO;
import com.x.bp.dao.po.ProductSkuDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
                productDTO.setPrice(ExchangeUtil.exchange(product.getPrice()));
                platformProductDTO.getProductDTOList().add(productDTO);
            });
        });

        return ServicePageResult.buildSuccess(resultList);
    }

    public List<ProductSkuDO> listSkuBySkuIds(List<Long> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ProductSkuDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProductSkuDO::getIsDelete, 0);
        lambdaQueryWrapper.in(ProductSkuDO::getId, skuIds);
        lambdaQueryWrapper.orderByDesc(ProductSkuDO::getId);
        List<ProductSkuDO> productSkuDOS = productSkuMapper.selectList(lambdaQueryWrapper);
        return Validator.value(productSkuDOS);
    }

    public List<ProductDO> listProductByProductIds(List<Long> productIds) {
        if (CollectionUtils.isEmpty(productIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDO::getStatus, 1);
        queryWrapper.eq(ProductDO::getIsDelete, 0);
        queryWrapper.in(ProductDO::getId, productIds);
        queryWrapper.orderByDesc(ProductDO::getId);
        return productMapper.selectList(queryWrapper);
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
        return ServicePageResult.buildSuccess(convertProductDetail(productDOS));
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

    public ServicePageResult<ProductDetailDTO> listProduct(ProductQueryReq req) {
        LambdaQueryWrapper<ProductDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ProductDO::getIsDelete, 0);
        lambdaQueryWrapper.eq(Validator.greaterZero(req.getPlatform()), ProductDO::getPlatform, req.getPlatform());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(req.getTitle()), ProductDO::getTitle, req.getTitle());
        IPage<ProductDO> iPage = productMapper.selectPage(new Page<>(req.getPageNo(), req.getPageSize()), lambdaQueryWrapper);
        if (null == iPage) {
            return ServicePageResult.buildSuccess();
        }
        if (CollectionUtils.isEmpty(iPage.getRecords())) {
            return ServicePageResult.buildSuccess(Collections.emptyList(), iPage.getTotal());
        }
        List<ProductDO> productDOS = iPage.getRecords();
        return ServicePageResult.buildSuccess(convertProductDetail(productDOS), iPage.getTotal());
    }

    private List<ProductDetailDTO> convertProductDetail(List<ProductDO> productDOS) {
        List<Long> productIds = productDOS.stream().map(ProductDO::getId).collect(Collectors.toList());
        List<ProductSkuDO> skuDOList = listSkuByProductIds(productIds);
        Map<Long, List<ProductSkuDO>> productSkuMap = skuDOList.stream().collect(Collectors.groupingBy(ProductSkuDO::getProductId));
        List<Long> platformIds = productDOS.stream().map(ProductDO::getPlatform).distinct().collect(Collectors.toList());
        List<PlatformDO> platformDOList = platformService.listByIds(platformIds);
        Map<Long, PlatformDO> platformDOMap = platformDOList.stream().collect(Collectors.toMap(PlatformDO::getId, Function.identity()));
        List<ProductDetailDTO> resultList = new ArrayList<>();
        productDOS.forEach(product -> {
            PlatformDO platformDO = platformDOMap.get(product.getPlatform());
            ProductDetailDTO detailDTO = new ProductDetailDTO();
            BeanUtils.copyProperties(product, detailDTO);
            detailDTO.setSkuDTOList(new ArrayList<>());
            detailDTO.setPlatformName(platformDO.getName());
            detailDTO.setPlatformNameEn(platformDO.getNameEn());
            detailDTO.setPrice(ExchangeUtil.exchange(product.getPrice()));
            resultList.add(detailDTO);
            List<ProductSkuDO> skuDOS = productSkuMap.get(product.getId());
            if (CollectionUtils.isEmpty(skuDOS)) {
                return;
            }
            skuDOS = skuDOS.stream().sorted(Comparator.comparing(ProductSkuDO::getSort)).collect(Collectors.toList());
            skuDOS.forEach(sku -> {
                ProductSkuDTO skuDTO = new ProductSkuDTO();
                BeanUtils.copyProperties(sku, skuDTO);
                skuDTO.setPrice(ExchangeUtil.exchange(sku.getPrice()));
                detailDTO.getSkuDTOList().add(skuDTO);
            });
        });
        return resultList;
    }

    public void offOrOnSaleProduct(Long id, ProductStatusEnum statusEnum) {
        ProductDO productDO = productMapper.selectById(id);
        if (null == productDO) {
            throw new CommonBizException("商品不存在");
        }
        productDO.setStatus(statusEnum.getStatus());
        productMapper.updateById(productDO);
    }

    public void deleteProduct(Long id) {
        ProductDO productDO = productMapper.selectById(id);
        if (null == productDO) {
            return;
        }
        productDO.setIsDelete(1);
        productMapper.updateById(productDO);
    }

    public void deleteProductSku(Long id) {
        ProductSkuDO productSkuDO = productSkuMapper.selectById(id);
        if (null == productSkuDO) {
            return;
        }
        productSkuDO.setIsDelete(1);
        productSkuMapper.updateById(productSkuDO);
    }

    public void addOrUpdateProduct(ProductVO productVO) {
        ProductDO productDO = new ProductDO();
        BeanUtils.copyProperties(productVO, productDO);
        if (Validator.greaterZero(productVO.getId())) {
            ProductDO existProduct = productMapper.selectById(productVO.getId());
            if (null == existProduct) {
                throw new CommonBizException("商品不存在");
            }
            productDO.setId(existProduct.getId());
            productMapper.updateById(productDO);
            return;
        }
        if (!Validator.greaterZero(productVO.getPlatform())) {
            throw new CommonBizException("参数错误 商品平台未指定");
        }
        if (StringUtils.isBlank(productVO.getTitle()) || StringUtils.isBlank(productVO.getTitleEn())) {
            throw new CommonBizException("参数错误 商品标题参数缺失");
        }
        productMapper.insert(productDO);
    }

    private void syncProductPrice(Long productId) {
        ProductDO product = productMapper.selectById(productId);
        List<ProductSkuDO> skuDOS = productSkuMapper.listSkuByProductId(productId);
        Long minPrice = 0L;
        for (ProductSkuDO skuDO : skuDOS) {
            minPrice = Math.min(skuDO.getPrice(), minPrice);
        }
        if (product.getPrice().equals(minPrice)) {
            return;
        }
        product.setPrice(minPrice);
        productMapper.updateById(product);
    }

    public void addOrUpdateSku(ProductSkuVO productSkuVO) {
        ProductSkuDO productSkuDO = new ProductSkuDO();
        BeanUtils.copyProperties(productSkuVO, productSkuDO);
        if (Validator.greaterZero(productSkuVO.getId())) {
            ProductSkuDO existProductSku = productSkuMapper.selectById(productSkuVO.getId());
            if (null == existProductSku) {
                throw new CommonBizException("商品sku不存在");
            }
            productSkuDO.setId(existProductSku.getId());
            if (null != productSkuVO.getPrice()) {
                if (productSkuVO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                    throw new CommonBizException("参数错误 sku价格参数异常");
                }
                productSkuDO.setPrice(PriceUtil.yuan2fen(productSkuVO.getPrice()));
            }
            if (null != productSkuVO.getTotal()) {
                //总库存变更 可售卖库存同步变更
                Integer diff = productSkuVO.getTotal() - existProductSku.getTotal();
                Integer stock = productSkuDO.getStock() + diff;
                productSkuDO.setStock(Math.max(stock, 0));
            }
            productSkuMapper.updateById(productSkuDO);
            syncProductPrice(existProductSku.getProductId());
            return;
        }
        if (!Validator.greaterZero(productSkuVO.getProductId())) {
            throw new CommonBizException("参数错误 sku归属商品未指定");
        }
        if (StringUtils.isBlank(productSkuVO.getAttributes()) || StringUtils.isBlank(productSkuVO.getAttributesEn())) {
            throw new CommonBizException("参数错误 sku属性参数缺失");
        }
        if (null == productSkuVO.getPrice() || productSkuVO.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new CommonBizException("参数错误 sku价格参数异常");
        }
        productSkuDO.setPrice(PriceUtil.yuan2fen(productSkuVO.getPrice()));
        productSkuDO.setStock(productSkuDO.getTotal());
        productSkuMapper.insert(productSkuDO);
        syncProductPrice(productSkuDO.getProductId());
    }
}
