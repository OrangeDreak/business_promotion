package com.x.bp.common.model;

import com.alibaba.fastjson.JSONObject;
import com.x.bp.common.utils.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class ServiceResultTO<T> {

    private Boolean success;

    private String message;

    private T data;

    private Integer code;


    public static <S> ServiceResultTO<S> buildSuccess(S data) {
        ServiceResultTO<S> resultTO = new ServiceResultTO<>();
        resultTO.setSuccess(true);
        resultTO.setData(data);
        resultTO.setCode(200);
        return resultTO;
    }

    public static <S> ServiceResultTO<S> buildSuccess() {
        ServiceResultTO<S> resultTO = new ServiceResultTO<>();
        resultTO.setSuccess(true);
        resultTO.setCode(200);
        return resultTO;
    }

    public static <S> ServiceResultTO<S> buildSuccess(S data, String message) {
        ServiceResultTO<S> resultTO = new ServiceResultTO<>();
        resultTO.setSuccess(true);
        resultTO.setData(data);
        resultTO.setMessage(message);
        resultTO.setCode(200);
        return resultTO;
    }

    public static <S> ServiceResultTO<S> buildFailed(String errorMsg) {
        ServiceResultTO<S> resultTO = new ServiceResultTO<>();
        resultTO.setSuccess(false);
        resultTO.setMessage(errorMsg);
        resultTO.setCode(500);
        return resultTO;
    }

    public static <S> ServiceResultTO<S> buildFailed(String errorMsg, Integer code) {
        ServiceResultTO<S> resultTO = new ServiceResultTO<>();
        resultTO.setSuccess(false);
        resultTO.setMessage(errorMsg);
        resultTO.setCode(Validator.greaterZero(code) ? code : 500);
        return resultTO;
    }

    public static <S> ServiceResultTO<S> buildFailed(String errorMsg, S data) {
        ServiceResultTO<S> resultTO = new ServiceResultTO<>();
        resultTO.setSuccess(false);
        resultTO.setMessage(errorMsg);
        resultTO.setData(data);
        return resultTO;
    }

    public boolean isSuccess() {
        return null != success && success;
    }

    public static <S> ServiceResultTO<S> parseResult(String context, Class<S> clazz) {
        try {
            ServiceResultTO<S> result = JSONObject.parseObject(context, ServiceResultTO.class);
            if (result.isSuccess()) {
                result.setData(JSONObject.parseObject(JSONObject.toJSONString(result.getData()), clazz));
            }
            return result;
        } catch (Exception e) {
            log.error("ServiceResultTO parseResult exception data:{}", context, e);
        }
        return ServiceResultTO.buildFailed("result data parse exception");
    }

    public static <S> ServiceResultTO<List<S>> parseArrayResult(String context, Class<S> clazz) {
        try {
            ServiceResultTO<List<S>> result = JSONObject.parseObject(context, ServiceResultTO.class);
            if (result.isSuccess()) {
                result.setData(JSONObject.parseArray(JSONObject.toJSONString(result.getData()), clazz));
            }
            return result;
        } catch (Exception e) {
            log.error("ServiceResultTO parseArrayResult exception data:{}", context, e);
        }
        return ServiceResultTO.buildFailed("result data parse exception");
    }
}
