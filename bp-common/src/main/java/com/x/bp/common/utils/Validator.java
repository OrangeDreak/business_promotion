package com.x.bp.common.utils;

import com.x.bp.common.constant.NumberConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author zouzhe
 * @CreateDate 2024/1/29 23:14
 * @Description
 */
@Slf4j
public final class Validator {
    private Validator() {

    }

    public static Long value(Long v) {
        return null == v ? NumberConstants.NUMBER_LONG_ZERO : v;
    }
    public static Integer value(Integer v) {
        return null == v ? NumberConstants.NUMBER_ZERO : v;
    }

    public static <T> List<T> value(List<T> list) {
        return null == list ? Collections.emptyList() : list;
    }

    public static boolean greaterZero(Long id) {
        if (null == id) {
            return false;
        }
        return id > NumberConstants.NUMBER_LONG_ZERO;
    }

    public static boolean greaterZero(Integer id) {
        if (null == id) {
            return false;
        }
        return id > NumberConstants.NUMBER_ZERO;
    }
    public static boolean isTrue(Boolean bool) {
        if (null == bool) {
            return false;
        }
        return bool;
    }

    public static boolean isFalse(Boolean bool) {
        return !isTrue(bool);
    }

    public static boolean isNullOrEmpty(Object value) {
        if (null == value) {
            return true;
        }
        if (value instanceof CharSequence) {
            if (value instanceof String && "null".equals(((String) value).toLowerCase())) {
                return true;
            }
            return isBlank((CharSequence) value);
        }
        if (isCollectionSupportType(value)) {
            return CollectionUtils.sizeIsEmpty(value);
        }
        return false;
    }

    public static boolean isNotNullOrEmpty(Object value) {
        return !isNullOrEmpty(value);
    }

    public static boolean isAllNullOrEmpty(Object... valueList) {
        for (Object value : valueList) {
            if (!isNullOrEmpty(value)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isAllNotNullOrEmpty(Object... valueList) {
        for (Object value : valueList) {
            if (isNullOrEmpty(value)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isNull(Object value) {
        return null == value;
    }
    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }
    public static boolean isAllNull(Object... valueList) {
        for (Object value : valueList) {
            if (!isNull(value)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isId(Long value) {
        return null != value && value > 0;
    }
    public static boolean isNotId(Long value) {
        return !isId(value);
    }
    public static boolean isId(Integer value) {
        return null != value && value > 0;
    }
    public static boolean isNotId(Integer value) {
        return !isId(value);
    }

    private static boolean isCollectionSupportType(Object value) {
        boolean isCollectionOrMap = value instanceof Collection || value instanceof Map;
        boolean isEnumerationOrIterator = value instanceof Enumeration || value instanceof Iterator;
        //集合或者map 枚举或者Iterator迭代器 判断数组
        return isCollectionOrMap || isEnumerationOrIterator || value.getClass().isArray();
    }

    public static String[] toStrArray(String str) {
        return toStrArray(",", str);
    }

    public static String[] toStrArray(String split, String str) {
        return isBlank(str) ? new String[0] : str.split(split);
    }
}
