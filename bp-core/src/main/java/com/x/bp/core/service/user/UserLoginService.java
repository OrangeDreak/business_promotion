package com.x.bp.core.service.user;

import com.x.bp.common.exception.CommonBizException;
import com.x.bp.common.enums.EnumError;
import com.x.bp.core.dto.user.UserRegisterReq;
import com.x.bp.core.enums.UserStatusEnum;
import com.x.bp.core.vo.user.UserLoginVO;
import com.x.bp.dao.po.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    public UserLoginVO login(String loginName, String password, Integer userType) {
        UserDO userDO = userService.getUserByLoginName(loginName, userType);
        if (Objects.isNull(userDO)) {
            throw new CommonBizException(EnumError.USER_NOT_EXISTS);
        }
        if (!userDO.getPassword().equals(password)) {//密码输入错误
            throw new CommonBizException(EnumError.PASSWORD_ERROR);
        }
        String token = tokenService.setToken(userDO.getId());
        UserLoginVO loginVO = new UserLoginVO();
        loginVO.setToken(token);
        loginVO.setUserType(userDO.getUserType());
        return loginVO;
    }

    public void register(UserRegisterReq req, Integer userType) {
        UserDO userDO = userService.getUserByLoginName(req.getEmail(), userType);
        if (null != userDO) {
            if (UserStatusEnum.LOG_OFF.getCode().equals(userDO.getStatus())) {
                userDO.setStatus(UserStatusEnum.NORMAL.getCode());
                userDO.setFirstName(StringUtils.isNotBlank(req.getFirstName()) ? req.getFirstName() : userDO.getFirstName());
                userDO.setLastName(StringUtils.isNotBlank(req.getLastName()) ? req.getLastName() : userDO.getLastName());
                userDO.setNickname(userDO.getFirstName() + " " + userDO.getLastName());
                userDO.setPassword(req.getPassword());
                userService.updateById(userDO);
                return;
            }
            throw new CommonBizException(EnumError.EMAIL_EXIST);
        }

        userDO = new UserDO();
        userDO.setEmail(req.getEmail());
        userDO.setFirstName(req.getFirstName());
        userDO.setLastName(req.getLastName());
        userDO.setNickname(req.getEmail());
        if (StringUtils.isNotBlank(req.getFirstName()) && StringUtils.isNotBlank(req.getLastName())) {
            userDO.setNickname(req.getFirstName() + " " + req.getLastName());
        }
        userDO.setUserType(userType);
        userDO.setPassword(req.getPassword());
        userService.addUser(userDO);
    }
}
