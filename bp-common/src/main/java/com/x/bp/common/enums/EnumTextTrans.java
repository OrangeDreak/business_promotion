package com.x.bp.common.enums;

import com.x.bp.common.exception.CommonBizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum EnumTextTrans {
    USER_PRIVATE_MESSAGE(1, "用户私信消息", "用户私信消息：%s", "User private message：%s",
        "【用户名称】：%s\n【用户邮箱】：%s\n【消息主题】：%s\n【消息内容】：%s",
            "【用户名称】：%s\n【用户邮箱】：%s\n【消息主题】：%s\n【消息内容】：%s"
    ),
    ;

    private Integer code;

    private String desc;

    private String title;

    private String titleEn;

    private String content;

    private String contentEn;

    public static String getTextTransTitle(EnumTextTrans enumTextTrans, Integer language, Object... args) {
        if (Objects.equals(LanguageTypeEnum.ZH.getCode(), language)) {
            if (Objects.nonNull(args)) {
                return String.format(enumTextTrans.title, args);
            }
            return enumTextTrans.title;
        }
        if (Objects.nonNull(args)) {
            return String.format(enumTextTrans.titleEn, args);
        }
        return enumTextTrans.titleEn;
    }

    public static String getTextTransContent(EnumTextTrans enumTextTrans, Integer language, Object... args) {
        if (Objects.equals(LanguageTypeEnum.ZH.getCode(), language)) {
            if (Objects.nonNull(args)) {
                return String.format(enumTextTrans.content, args);
            }
            return enumTextTrans.content;
        }
        if (Objects.nonNull(args)) {
            return String.format(enumTextTrans.contentEn, args);
        }
        return enumTextTrans.contentEn;
    }

    public static EnumTextTrans getByCode(Integer code) {
        return Arrays.stream(values())
                .filter(s -> Objects.equals(s.getCode(), code))
                .findFirst()
                .orElseThrow(() -> new CommonBizException("邮件类型不存在"));
    }
}
