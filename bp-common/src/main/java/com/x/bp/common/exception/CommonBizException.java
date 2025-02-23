package com.x.bp.common.exception;

import com.x.bp.common.context.ApiContext;
import com.x.bp.common.enums.EnumError;
import com.x.bp.common.enums.LanguageTypeEnum;
import com.x.bp.common.utils.ApiContextUtil;

/**
 * @author by yongyuan @date 2023/11/28.
 */
public class CommonBizException extends RuntimeException {

    private Integer errorCode;

    private final String errorMessage;

    public CommonBizException(EnumError errorEnum) {
        super(errorEnum.getName());
        String msg = LanguageTypeEnum.ZH.getEnName().equals(ApiContextUtil.getLange()) ? errorEnum.getName() : errorEnum.getNameEn();
        this.errorMessage = msg;
        this.errorCode = errorEnum.getValue();
    }

    public CommonBizException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public CommonBizException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public CommonBizException(Throwable cause) {
        super(cause);
        this.errorMessage = cause.getMessage();
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

}
