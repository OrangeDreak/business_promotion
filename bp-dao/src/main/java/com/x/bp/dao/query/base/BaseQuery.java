package com.x.bp.dao.query.base;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
public class BaseQuery {

    @ApiModelProperty("分页")
    int pageNo = 1;

    @ApiModelProperty("分页大小")
    int pageSize = 20;

    @ApiModelProperty("偏移")
    int offset = 0;

    public int getOffset(){
        return (pageNo - 1) * pageSize;
    }


    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
