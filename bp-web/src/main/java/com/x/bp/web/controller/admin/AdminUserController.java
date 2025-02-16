package com.x.bp.web.controller.admin;

import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.ApiContextUtil;
import com.x.bp.core.dto.user.UserQueryReq;
import com.x.bp.core.service.user.UserService;
import com.x.bp.core.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户管理
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@RestController
@RequestMapping("/admin/user")
@Api(value = "后台用户管理")
public class AdminUserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUserInfo")
    public ServiceResultTO<UserVO> getUserInfo() {
        return userService.getUserInfo(ApiContextUtil.getUserId());
    }

    @ApiOperation(value = "客户用户列表")
    @GetMapping("/customerUserList")
    public ServicePageResult<UserVO> customerUserList(@RequestBody @Validated UserQueryReq req) {
        return userService.customerUserList(req);
    }

    @ApiOperation(value = "用户详情")
    @GetMapping("/detail")
    public ServiceResultTO<UserVO> detail(@RequestParam(name = "id") Long id) {
        return userService.getUserInfo(id);
    }


}
