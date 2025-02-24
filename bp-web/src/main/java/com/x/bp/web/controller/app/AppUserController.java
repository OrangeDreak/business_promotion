package com.x.bp.web.controller.app;

import com.x.bp.common.enums.UserTypeEnum;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.common.utils.ApiContextUtil;
import com.x.bp.core.common.Result;
import com.x.bp.core.dto.message.MessageDTO;
import com.x.bp.core.dto.user.UserLoginReq;
import com.x.bp.core.service.message.MessageService;
import com.x.bp.core.service.user.UserService;
import com.x.bp.core.vo.user.UserLoginVO;
import com.x.bp.core.vo.user.UserVO;
import com.x.bp.web.annotion.LoginNotRequired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/app/user")
@Api(value = "用户管理")
public class AppUserController {
    @Resource
    private UserService userService;
    @Resource
    private MessageService messageService;

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getUserInfo")
    public ServiceResultTO<UserVO> getUserInfo() {
        return userService.getUserInfo(ApiContextUtil.getUserId());
    }


    @ApiOperation("发送消息")
    @PostMapping("/sendMsg")
    @LoginNotRequired
    public ServiceResultTO<Boolean> sendMsg(@RequestBody @Validated MessageDTO req) {
        req.setUserId(ApiContextUtil.getUserId());
        messageService.addMessage(req);
        return ServiceResultTO.buildSuccess(true);
    }

}
