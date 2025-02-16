package com.x.bp.web.controller.admin;

import com.x.bp.common.enums.ProductStatusEnum;
import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.core.dto.product.ProductDetailDTO;
import com.x.bp.core.dto.user.ProductBaseReq;
import com.x.bp.core.dto.user.ProductQueryReq;
import com.x.bp.core.service.product.PlatformService;
import com.x.bp.core.service.product.ProductService;
import com.x.bp.core.vo.product.PlatformVO;
import com.x.bp.core.vo.product.ProductSkuVO;
import com.x.bp.core.vo.product.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 商品管理
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@RestController
@RequestMapping("/admin/product")
@Api(value = "商品管理")
public class AdminProductController {
    @Resource
    private ProductService productService;
    @Resource
    private PlatformService platformService;

    @ApiOperation(value = "平台列表")
    @GetMapping("/listValidPlatform")
    public ServicePageResult<PlatformVO> listValidPlatform() {
        return ServicePageResult.buildSuccess(platformService.listValidPlatform());
    }


    @ApiOperation(value = "商品列表")
    @GetMapping("/listProduct")
    public ServicePageResult<ProductDetailDTO> listProduct(@RequestBody @Validated ProductQueryReq req) {
        return productService.listProduct(req);
    }

    @ApiOperation(value = "商品下架")
    @PostMapping("/offSale")
    public ServiceResultTO<Boolean> offSale(@RequestBody @Validated ProductBaseReq req) {
        productService.offOrOnSaleProduct(req.getId(), ProductStatusEnum.OFF_SALE);
        return ServiceResultTO.buildSuccess(true);
    }

    @ApiOperation(value = "商品上架")
    @PostMapping("/onSale")
    public ServiceResultTO<Boolean> onSale(@RequestBody @Validated ProductBaseReq req) {
        productService.offOrOnSaleProduct(req.getId(), ProductStatusEnum.ON_SALE);
        return ServiceResultTO.buildSuccess(true);
    }

    @ApiOperation(value = "商品删除")
    @PostMapping("/deleteProduct")
    public ServiceResultTO<Boolean> deleteProduct(@RequestBody @Validated ProductBaseReq req) {
        productService.deleteProduct(req.getId());
        return ServiceResultTO.buildSuccess(true);
    }

    @ApiOperation(value = "新增或编辑商品")
    @PostMapping("/addOrUpdate")
    public ServiceResultTO<Boolean> addOrUpdate(@RequestBody @Validated ProductVO productVO) {
        productService.addOrUpdateProduct(productVO);
        return ServiceResultTO.buildSuccess(true);
    }

    @ApiOperation(value = "新增或编辑商品Sku")
    @PostMapping("/addOrUpdateSku")
    public ServiceResultTO<Boolean> addOrUpdateSku(@RequestBody @Validated ProductSkuVO productSkuVO) {
        productService.addOrUpdateSku(productSkuVO);
        return ServiceResultTO.buildSuccess(true);
    }

    @ApiOperation(value = "商品Sku删除")
    @PostMapping("/deleteProductSku")
    public ServiceResultTO<Boolean> deleteProductSku(@RequestBody @Validated ProductBaseReq req) {
        productService.deleteProductSku(req.getId());
        return ServiceResultTO.buildSuccess(true);
    }

}
