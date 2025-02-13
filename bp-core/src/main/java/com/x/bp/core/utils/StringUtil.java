package com.x.bp.core.utils;

import com.x.bp.common.exception.CommonBizException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 */
public class StringUtil {

    private static Pattern pattern = Pattern.compile("-?[0-9]+(\\\\.[0-9]+)?");

    private final static String aesKey = "sifubuy_wms_encode_mobile";

    private final static String passwordRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";

    /**
     * 判断密码格式
     * @return
     */
    public static void checkPassword(String password) {
        if (!password.matches(passwordRegex)) {
            throw new CommonBizException("密码必须包含字母和数字");
        }
    }

    /**
     * 判断字符串是否是数字
     * @return
     */
    public static boolean isNumber(String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 手机号脱敏处理
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    /**
     * 加密手机号
     * @param mobile
     * @return
     */
    public static String encodeMobile(String mobile){
        if (StringUtils.isBlank(mobile)){
            return mobile;
        }
        return EncryptUtil.getInstance().AESencode(mobile,aesKey);
    }

    /**
     * 解密手机号
     * @param encodeStr
     * @return
     */
    public static String decodeMobile(String encodeStr){
        if (StringUtils.isBlank(encodeStr)){
            return encodeStr;
        }
        return EncryptUtil.getInstance().AESdecode(encodeStr,aesKey);
    }

    public static String toStringDefaultEmpty(Long str) {
        if (Objects.isNull(str)) {
            return StringUtils.EMPTY;
        }

        return String.valueOf(str);
    }

    /**
     * 提取字符串中的数字
     *
     * @param str str
     * @return 数字集合
     */
    public static List<Long> extractNum(String str) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        return Arrays.stream(str.replaceAll("[^0-9]", ",").split(","))
                .filter(StringUtils::isNotBlank)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

}
