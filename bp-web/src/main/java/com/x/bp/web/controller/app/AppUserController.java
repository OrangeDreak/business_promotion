package com.x.bp.web.controller.app;

import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.ApiContextUtil;
import com.x.bp.core.service.user.UserService;
import com.x.bp.core.vo.user.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户管理
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户管理")
public class AppUserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUserInfo")
    public ServiceResultTO<UserVO> getUserInfo() {
        return userService.getUserInfo(ApiContextUtil.getUserId());
    }


}
