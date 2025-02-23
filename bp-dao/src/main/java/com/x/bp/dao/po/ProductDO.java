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
@TableName(value = "bp_product",autoResultMap = true)
public class ProductDO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题
     */
    private String titleEn;

    /**
     * 描述
     */
    private String productDesc;

    /**
     * 描述
     */
    private String descEn;

    /**
     * 图片
     */
    private String image;

    /**
     * 1上架；0下架
     */
    private Integer status;
    /**
     * 价格
     */
    private Long price;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 平台
     */
    private Long platform;

    /**
     * 服务标签
     */
    private String serviceTag;

    /**
     * 服务标签
     */
    private String serviceTagEn;

    private String propSelectText;

    private String propSelectTextEn;

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