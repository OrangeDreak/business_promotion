package com.x.bp.common.utils;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:19
 * @Description
 */
public class OrderNoUtils {

    public static String CUSTOMER_ORDER_NO_PREFIX = "BP";

    public static String CUSTOMER_SUB_ORDER_NO_PREFIX = "BI";

    public static String getCustomerOrderNo() {
        return CUSTOMER_ORDER_NO_PREFIX + SnowFlakeIdGenerator.nextId();
    }

    public static String getCustomerSubOrderNo() {
        return CUSTOMER_SUB_ORDER_NO_PREFIX + SnowFlakeIdGenerator.nextId();
    }

}

