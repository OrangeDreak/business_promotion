package com.x.bp.core.dto.user;

import com.x.bp.dao.query.base.BaseQuery;
import lombok.Data;

@Data
public class ProductQueryReq extends BaseQuery {

    /**
     * 标题
     */
    private String title;

    /**
     * 平台
     */
    private Integer platform;
}
