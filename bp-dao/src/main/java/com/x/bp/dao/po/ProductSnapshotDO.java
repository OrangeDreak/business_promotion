package com.x.bp.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 14:29
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "bp_product_snapshot",autoResultMap = true)
public class ProductSnapshotDO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long subOrderId;

    private Long platform;

    private Long productId;

    private Long skuId;

    private String title;

    private String titleEn;

    private String attributes;

    private String attributesEn;

    private Long price;

    private Integer skuCount;

    private Integer isDelete;

    private Date gmtCreate;

    private Date gmtModified;
}
