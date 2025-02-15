package com.x.bp.common.model;

import com.alibaba.fastjson.JSONObject;
import com.x.bp.common.utils.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Data
@Slf4j
public class ServicePageResult<T> {
    private long total;
    private List<T> data;
    private int code;
    private Boolean success;
    private String message;

    public static <S> ServicePageResult<S> buildSuccess(List<S> list, long total) {
        ServicePageResult<S> pageResult = new ServicePageResult<>();
        pageResult.setData(list);
        pageResult.setTotal(total);
        pageResult.setSuccess(true);
        pageResult.setCode(200);
        return pageResult;
    }

    public static <S> ServicePageResult<S> buildSuccess(List<S> list) {
        ServicePageResult<S> pageResult = new ServicePageResult<>();
        pageResult.setData(list);
        pageResult.setSuccess(true);
        pageResult.setCode(200);
        return pageResult;
    }

    public static <S> ServicePageResult<S> buildSuccess() {
        ServicePageResult<S> pageResult = new ServicePageResult<>();
        pageResult.setData(Collections.emptyList());
        pageResult.setSuccess(true);
        pageResult.setCode(200);
        return pageResult;
    }

    public static <S> ServicePageResult<S> buildFailed(String errorMsg) {
        ServicePageResult<S> pageResult = new ServicePageResult<>();
        pageResult.setSuccess(false);
        pageResult.setMessage(errorMsg);
        pageResult.setCode(500);
        return pageResult;
    }

    public static <S> ServicePageResult<S> buildFailed(Integer errorCode, String errorMsg) {
        ServicePageResult<S> pageResult = new ServicePageResult<>();
        pageResult.setSuccess(false);
        pageResult.setMessage(errorMsg);
        pageResult.setCode(Validator.greaterZero(errorCode) ?  errorCode : 500);
        return pageResult;
    }

    public boolean isSuccess() {
        return null != success && success;
    }

    public static <S> ServicePageResult<S> parsePageResult(String context, Class<S> clazz) {
        try {
            ServicePageResult<S> result = JSONObject.parseObject(context, ServicePageResult.class);
            if (result.isSuccess()) {
                result.setData(JSONObject.parseArray(JSONObject.toJSONString(result.getData()), clazz));
            }
            return result;
        } catch (Exception e) {
            log.error("ServicePageResult parsePageResult exception data:{}", context, e);
        }
        return ServicePageResult.buildFailed("result data parse exception");
    }
}
