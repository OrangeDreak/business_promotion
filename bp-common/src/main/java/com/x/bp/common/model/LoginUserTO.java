/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.x.bp.common.model;

import lombok.Data;

/**
 * 实体类
 *
 * @author Chill
 */
@Data
public class LoginUserTO {
	private Long id;

	/**
	 * 名称
	 */
	private String firstName;

	/**
	 * 名称
	 */
	private String lastName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 用户类型 0：C端用户 1：后台用户
	 */
	private Integer userType;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 加密手机号
	 */
	private String encryptedMobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 0正常；1冻结；2待激活；3已注销
	 */
	private Integer status;

}
