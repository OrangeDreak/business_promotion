package com.x.bp.core.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Data
public class ResultCallback<T> {

    @ApiModelProperty("返回标记：成功标记=1，失败标记=0")
    int code;

    @ApiModelProperty("返回信息")
    String msg;

    @ApiModelProperty("数据")
    T data;

    public static <T> ResultCallback<T> buildSuccess(T data) {
        ResultCallback<T> result = new ResultCallback<>();
        result.code = 1;
        result.data = data;
        return result;
    }

    public static <T> ResultCallback<T> buildSuccess() {
        ResultCallback<T> result = new ResultCallback<>();
        result.code = 1;
        return result;
    }


    public static <T> ResultCallback<T> buildFail(String msg) {
        ResultCallback<T> result = new ResultCallback<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}
