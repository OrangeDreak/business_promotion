package com.x.bp.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumYesOrNo {
    YES(1,true),
    NO(0,false),;

    private Integer code;

    private Boolean bool;

}
