package com.sifubuy.wms.web.base;

import com.x.bp.common.exception.CommonBizException;
import com.x.bp.core.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 系统统一异常处理框架
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(CommonBizException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<String> handleBizException(CommonBizException ex){
        log.error("catch bizException", ex);
        return Result.buildFail(ex.getMessage(), ex.getErrorCode());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<String> handleException(Exception ex) {
        log.info("error {}", ex.getStackTrace(), ex);
        log.info("catch exception", ExceptionUtils.getStackTrace(ex));
        return Result.buildFail("系统错误");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.error("catch MethodArgumentNotValidException", ex);
        FieldError fieldError = ex.getBindingResult().getFieldErrors().get(0);
        return Result.buildFail(fieldError.getDefaultMessage());
    }
}
