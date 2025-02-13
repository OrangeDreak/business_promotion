package com.x.bp.common.utils;

import com.x.bp.common.constant.NumberConstants;
import com.x.bp.common.context.ApiContext;
import com.x.bp.common.enums.LanguageTypeEnum;
import com.x.bp.common.model.LoginUserTO;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
public class ApiContextUtil {
    public static LoginUserTO getSubject() {
        return ApiContext.getContext().getLoginUser();
    }

    public static Long getUserId() {
        LoginUserTO loginUserTO = ApiContext.getContext().getLoginUser();
        return null == loginUserTO ? NumberConstants.NUMBER_LONG_ZERO : loginUserTO.getId();
    }

    public static String getLange() {
        String lan = ApiContext.getContext().getLange();
        return StringUtils.isBlank(lan) ? LanguageTypeEnum.EN.getEnName() : lan;
    }
}
