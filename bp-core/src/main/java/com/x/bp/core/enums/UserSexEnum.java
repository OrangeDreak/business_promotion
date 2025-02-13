package com.x.bp.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * 用户性别
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Getter
@AllArgsConstructor
public enum UserSexEnum {
	MAN(1, "男"),

	WOMAN(2, "女"),

	OTHER(-1, "未知"),

	;

	private final Integer code;
	private final String desc;

	public static UserSexEnum getByCode(Integer code) {
		return Arrays.stream(values())
			.filter(s -> Objects.equals(s.getCode(), code))
			.findFirst()
			.orElse(OTHER);
	}
}
