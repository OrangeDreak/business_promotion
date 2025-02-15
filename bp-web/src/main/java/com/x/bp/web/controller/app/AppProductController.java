package com.x.bp.web.controller.app;

import com.x.bp.common.model.ServicePageResult;
import com.x.bp.core.dto.product.PlatformProductDTO;
import com.x.bp.core.dto.product.ProductDetailDTO;
import com.x.bp.core.service.product.ProductService;
import com.x.bp.web.annotion.LoginNotRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 商品管理
 *
 * @author: lhg
 * @date: 2024/9/29
 */
@RestController
@RequestMapping("/app/product")
@Api(value = "商品管理")
public class AppProductController {
    @Resource
    private ProductService productService;


    @ApiOperation(value = "所有平台商品列表")
    @GetMapping("/all-platform-product")
    @LoginNotRequired
    public ServicePageResult<PlatformProductDTO> allPlatformProduct() {
        return productService.listAllPlatformProduct();
    }

    @ApiOperation(value = "平台商品列表")
    @GetMapping("/platform-product")
    @LoginNotRequired
    public ServicePageResult<ProductDetailDTO> platformProduct(@RequestParam(name = "platform") Long platform) {
        return productService.listPlatformProduct(platform);
    }

}
