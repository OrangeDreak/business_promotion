package com.x.bp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author tianhe
 * @Description 语言
 * @date 2023/11/30 2:17 PM
 */
@Getter
@AllArgsConstructor
public enum LanguageTypeEnum {
    ZH(0, "简体中文", "zh", "zh_CN"),
    EN(1, "English", "en", "en_US"),
    JA(2, "日本語", "ja", "ja_JP"),
    KO(3, "한국어", "ko", "ko_KR"),//韩语
    RU(4, "Русский", "ru", "ru_RU"),//俄罗斯语言
    VI(5, "Việt Nam", "vi", "vi_VN"),//越南
    FR(6, "En français", "fr", "fr_FR"),//法语
    PT(7, "português", "pt", "pt_PT"),//葡萄牙语
    ES(8, "español", "es", "es_ES"),//西班牙语
    AR(9, "阿拉伯语", "ar", ""),
    CKB(10, "库尔德语", "ckb", ""),

    ;
    private Integer code;
    private String name;
    private String enName;
    private String fullName; 

    public static LanguageTypeEnum getByEnName(String enName) {
        return Arrays.stream(values())
                .filter(s -> Objects.equals(s.getEnName(), enName))
                .findFirst()
                .orElse(EN);
    }

    public static LanguageTypeEnum getByCode(Integer code) {
        return Arrays.stream(values())
                .filter(s -> Objects.equals(s.getCode(), code))
                .findFirst()
                .orElse(EN);
    }
}
