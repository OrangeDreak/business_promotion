package com.x.bp.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.bp.dao.po.ProductSkuDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductSkuMapper extends BaseMapper<ProductSkuDO> {
    @Select("select * from bp_product_sku where is_delete = 0 and product_id = #{productId}")
    List<ProductSkuDO> listSkuByProductId(@Param("productId") Long productId);

}