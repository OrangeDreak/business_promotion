package com.x.bp.web.controller.admin;

import com.x.bp.web.annotion.LoginNotRequired;
import com.x.bp.common.enums.UserTypeEnum;
import com.x.bp.core.common.Result;
import com.x.bp.core.dto.user.UserLoginReq;
import com.x.bp.core.service.user.UserLoginService;
import com.x.bp.core.vo.user.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录管理
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@RestController
@RequestMapping("/admin/user")
@Api(value = "登录管理")
public class AdminLoginController {
    @Resource
    private UserLoginService userLoginService;

    @ApiOperation("登录")
    @PostMapping("/login")
    @LoginNotRequired
    public Result<UserLoginVO> login(@RequestBody @Validated UserLoginReq req) {
        UserLoginVO userLoginVO = userLoginService.login(req.getLoginName(), req.getPassword(), UserTypeEnum.ADMINISTRATOR.getType());
        return Result.buildSuccess(userLoginVO);
    }
}
