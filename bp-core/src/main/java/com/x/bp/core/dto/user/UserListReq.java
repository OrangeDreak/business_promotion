package com.x.bp.core.dto.user;

import com.x.bp.dao.query.base.BaseQuery;
import lombok.Data;

/**
 * @author tianhe
 * @Description
 * @date 2023/12/1 6:50 PM
 */

@Data
public class UserListReq extends BaseQuery {
	/**
	 * 用户id
	 */
	private Long id;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;

}
