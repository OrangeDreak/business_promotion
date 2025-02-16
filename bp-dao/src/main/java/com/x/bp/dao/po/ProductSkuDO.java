package com.x.bp.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "bp_product_sku",autoResultMap = true)
public class ProductSkuDO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 规格属性
     */
    private String attributes;

    /**
     * 规格属性
     */
    private String attributesEn;

    /**
     * 图片
     */
    private String image;

    /**
     * 总库存
     */
    private Integer total;

    /**
     * 可售卖库存
     */
    private Integer stock;

    /**
     * 已售卖库存
     */
    private Integer sold;

    /**
     * 已售卖库存
     */
    private Long price;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否删除
     */
    private Integer isDelete;
    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}