package com.x.bp.web.controller.user;

import com.x.bp.core.service.user.UserService;
import io.swagger.annotations.Api;
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
public class UserController {
    @Resource
    private UserService userService;

//    @ApiOperation(value = "添加用户")
//    @PostMapping("/addUser")
//    @LoginNotRequired
//    public Result<Boolean> addUser(@RequestBody @Validated AddUserReq req) {
//        return userService.addUser(req);
//    }
//
//    @ApiOperation(value = "获取用户信息")
//    @GetMapping("/getUserInfo")
//    public Result<UserVO> getUserInfo() {
//        return userService.getUserInfo(ApiContextUtil.getUserId());
//    }
//
//    @ApiOperation(value = "修改密码")
//    @PostMapping("/updatePassword")
//    public Result<Boolean> updatePassword(@RequestBody @Validated UpdatePasswordReq req) {
//        return userService.updatePassword(req);
//    }
//
//    @ApiOperation(value = "修改个人信息")
//    @PostMapping("/updateUserInfo")
//    public Result<Boolean> updateUserInfo(@RequestBody @Validated UpdateUserInfoReq req) {
//        if (StringUtils.isBlank(req.getNickname())
//                && StringUtils.isBlank(req.getAvatar())
//                && req.getSex() == null) {
//            return Result.buildFail(EnumError.PARAMS_ERROR.getName());
//        }
//        return userService.updateUserInfo(req);
//    }
//
//    @ApiOperation(value = "用户列表")
//    @PostMapping("/list")
//    public Result<IPage<UserDO>> list(@RequestBody @Validated UserListReq req) {
//        return userService.list(req);
//    }
//
//    @ApiOperation(value = "用户详情")
//    @GetMapping("/detail")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "用户id", paramType = "query", dataType = "integer"),
//    })
//    public Result<UserVO> detail(@RequestParam(name = "userId") Long userId) {
//        return userService.getUserInfo(userId);
//    }
//
//    @ApiOperation(value = "冻结用户")
//    @PostMapping("/freeze")
//    public Result<Boolean> freeze(@RequestBody @Validated FreezeReq req) {
//        return userService.freeze(req);
//    }
//
//    @ApiOperation(value = "修改用户备注")
//    @PostMapping("/updateRemark")
//    public Result<Boolean> updateRemark(@RequestBody @Validated UpdateRemarkReq req) {
//        return userService.updateRemark(req);
//    }
//
//    /**
//     * 设置菜单权限
//     * @param req
//     * @return
//     */
//    @PostMapping("/grant")
//    @ApiOperation(value = "权限设置", notes = "传入roleId集合以及userId集合")
//    public ServiceResultTO<Boolean> grant(@Valid @RequestBody UserRoleGrantReq req) {
//        userService.grant(req);
//        return ServiceResultTO.buildSuccess();
//    }
//
//    /**
//     * 修改
//     */
//    @PostMapping("/update")
//    @ApiOperation(value = "修改", notes = "传入User")
//    public ServiceResultTO<Boolean> update(@Valid @RequestBody UserVO user) {
//        userService.updateUser(user);
//        return ServiceResultTO.buildSuccess();
//    }

}
