package com.x.bp.core.dto.user;

import com.x.bp.dao.query.base.BaseQuery;
import lombok.Data;

@Data
public class UserQueryReq extends BaseQuery {

    /**
     * 关键词 用户名或者邮箱
     */
    private String keyword;
}
