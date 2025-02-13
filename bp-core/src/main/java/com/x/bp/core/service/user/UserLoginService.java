package com.x.bp.core.service.user;

import com.x.bp.common.exception.CommonBizException;
import com.x.bp.common.enums.EnumError;
import com.x.bp.dao.po.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Slf4j
@Service
public class UserLoginService {
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;

    private static final long PASSWORD_COUNT = 5L;
    private static final long PASSWORD_TODAY_COUNT = 20L;

    public String login(String loginName, String password, Integer userType) {
        UserDO userDO = userService.getUserByLoginName(loginName, userType);
        if (Objects.isNull(userDO)) {
            throw new CommonBizException(EnumError.USER_NOT_EXISTS);
        }
        if (!userDO.getPassword().equals(password)) {//密码输入错误
            throw new CommonBizException(EnumError.PASSWORD_ERROR);
        }
        return tokenService.setToken(userDO.getId());
    }
}
