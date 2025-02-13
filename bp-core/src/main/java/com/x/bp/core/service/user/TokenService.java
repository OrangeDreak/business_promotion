package com.x.bp.core.service.user;

import com.x.bp.common.model.LoginUserTO;
import com.x.bp.common.utils.TimeUtils;
import com.x.bp.dao.mapper.TokenMapper;
import com.x.bp.dao.po.TokenDO;
import com.x.bp.dao.po.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Component
public class TokenService {
    @Resource
    private TokenMapper tokenMapper;
    @Resource
    private UserService userService;

    public static int TOKEN_EXPIRE = 60 * 60 * 24 * 7;

    public String setToken(Long userId) {
        String token = UUID.randomUUID().toString();
        TokenDO tokenDO = new TokenDO();
        tokenDO.setToken(token);
        tokenDO.setUserId(userId);
        tokenDO.setExpireTime(TimeUtils.getCurrentTime() + TOKEN_EXPIRE);
        tokenMapper.insert(tokenDO);
        return token;
    }

    public LoginUserTO getUserInfo(String token) {
        if (StringUtils.isBlank(token)) {
            return new LoginUserTO();
        }
        TokenDO tokenDO = tokenMapper.getByToken(token);
        if (null == tokenDO) {
            return null;
        }
        if (TimeUtils.getCurrentTime() > tokenDO.getExpireTime()) {
            return null;
        }
        UserDO userDO = userService.getById(tokenDO.getUserId());
        LoginUserTO userInfo = new LoginUserTO();
        BeanUtils.copyProperties(userDO, userInfo);
        tokenDO.setExpireTime(TimeUtils.getCurrentTime() + TOKEN_EXPIRE);
        tokenDO.setGmtModified(null);
        tokenMapper.updateById(tokenDO);
        return userInfo;
    }

    public void deleteToken(Long userId) {
        tokenMapper.deleteByUserId(userId);
    }
}
