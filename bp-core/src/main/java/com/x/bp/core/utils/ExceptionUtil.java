package com.x.bp.core.utils;

import com.x.bp.common.enums.EnumError;
import com.x.bp.common.exception.CommonBizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

public class ExceptionUtil {

    /**
     * 空校验
     *
     * @param object
     * @param msg
     */
    public static void isNull(Object object, String msg) {
        if (Objects.isNull(object)) {
            throw new CommonBizException(msg);
        }
    }

    public static void isNull(Object object, EnumError enumError) {
        if (Objects.isNull(object)) {
            throw new CommonBizException(enumError.getName());
        }
    }

    /**
     * 字符串空校验
     *
     * @param object
     * @param msg
     */
    public static void isNull(String object, String msg) {
        if (StringUtils.isEmpty(object)) {
            throw new CommonBizException(msg);
        }
    }

    /**
     * 空校验
     *
     * @param object
     * @param msg
     */
    public static void isNotNull(Object object, String msg) {
        if (Objects.nonNull(object)) {
            throw new CommonBizException(msg);
        }
    }

    public static void isNotNull(Object object, EnumError enumError) {
        if (Objects.nonNull(object)) {
            throw new CommonBizException(enumError.getName());
        }
    }

    /**
     * 字符串空校验
     *
     * @param object
     * @param msg
     */
    public static void isNotNull(String object, String msg) {
        if (StringUtils.isNotEmpty(object)) {
            throw new CommonBizException(msg);
        }
    }


    /**
     * 空集合校验
     *
     * @param coll
     * @param msg
     */
    public static void isEmpty(Collection coll, String msg) {
        if (CollectionUtils.isEmpty(coll)) {
            throw new CommonBizException(msg);
        }

    }

    public static void isEmpty(Collection<?> coll, EnumError enumError) {
        if (CollectionUtils.isEmpty(coll)) {
            throw new CommonBizException(enumError.getName());
        }
    }

    public static void isNotEmpty(Collection coll, String msg) {
        if (!CollectionUtils.isEmpty(coll)) {
            throw new CommonBizException(msg);
        }
    }

    public static void isNotEmpty(Collection<?> coll, EnumError enumError) {
        if (!CollectionUtils.isEmpty(coll)) {
            throw new CommonBizException(enumError.getName());
        }
    }

    /**
     * 条件匹配校验
     *
     * @param expression
     * @param msg
     */
    public static void isTrue(boolean expression, String msg) {
        if (expression) {
            throw new CommonBizException(msg);
        }
    }

    public static void isTrue(boolean expression, EnumError enumError) {
        if (expression) {
            throw new CommonBizException(enumError.getName());
        }
    }

    /**
     * 条件匹配校验
     *
     * @param expression
     * @param msg
     */
    public static void isFalse(boolean expression, String msg) {
        if (!expression) {
            throw new CommonBizException(msg);
        }
    }

    public static void isFalse(boolean expression, EnumError enumError) {
        if (!expression) {
            throw new CommonBizException(enumError.getName());
        }
    }

    /**
     * 字符串空校验
     *
     * @param object
     * @param msg
     */
    public static void isBlank(String object, String msg) {
        if (StringUtils.isBlank(object)) {
            throw new CommonBizException(msg);
        }
    }
}
