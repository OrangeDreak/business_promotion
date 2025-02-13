package com.x.bp.core.service.user;

import com.x.bp.common.enums.UserTypeEnum;
import com.x.bp.core.common.Result;
import com.x.bp.core.vo.user.UserVO;
import com.x.bp.dao.mapper.UserMapper;
import com.x.bp.dao.po.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Slf4j
@Service
public class UserService {
    @Value("${system.defaultPassword:123456}")
    private String defaultPassword;
    @Resource
    private UserMapper userMapper;

    public Result<UserVO> getUserInfo(Long userId) {
        UserDO userDO = getById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return Result.buildSuccess(userVO);
    }

    public UserDO getById(Long id) {
        return userMapper.selectById(id);
    }

    public UserDO getUserByLoginName(String loginName, Integer userType) {
        if (StringUtils.isBlank(loginName) || UserTypeEnum.isValidType(userType)) {
            return null;
        }
        return userMapper.getUserByLoginName(loginName, userType);
    }

    public void updateById(UserDO userDO) {
        userDO.setGmtModified(null);
        userMapper.updateById(userDO);
    }

    public void addUser(UserDO userDO) {
        userMapper.insert(userDO);
    }
}
