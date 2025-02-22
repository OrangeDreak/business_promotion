package com.x.bp.core.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.x.bp.common.enums.UserTypeEnum;
import com.x.bp.common.model.ServicePageResult;
import com.x.bp.common.model.ServiceResultTO;
import com.x.bp.core.dto.user.UserQueryReq;
import com.x.bp.core.vo.user.UserVO;
import com.x.bp.dao.mapper.UserMapper;
import com.x.bp.dao.po.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    public ServiceResultTO<UserVO> getUserInfo(Long userId) {
        UserDO userDO = getById(userId);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return ServiceResultTO.buildSuccess(userVO);
    }

    public UserDO getById(Long id) {
        return userMapper.selectById(id);
    }

    public List<UserDO> listByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    public UserDO getUserByLoginName(String loginName, Integer userType) {
        if (StringUtils.isBlank(loginName) || !UserTypeEnum.isValidType(userType)) {
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

    public ServicePageResult<UserVO> customerUserList(UserQueryReq req) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getIsDelete, 0);
        queryWrapper.eq(UserDO::getUserType, UserTypeEnum.CUSTOMER.getType());
        if (StringUtils.isNotBlank(req.getKeyword())) {
            queryWrapper.and(wq -> {
                wq.like(UserDO::getNickname, req.getKeyword()).or().like(UserDO::getEmail, req.getKeyword());
            });
        }
        queryWrapper.orderByDesc(UserDO::getId);
        IPage<UserDO> iPage = userMapper.selectPage(new Page<>(req.getPageNo(), req.getPageSize()), queryWrapper);
        if (null == iPage) {
            return ServicePageResult.buildSuccess(Collections.emptyList(), 0);
        }
        if (CollectionUtils.isEmpty(iPage.getRecords())) {
            return ServicePageResult.buildSuccess(Collections.emptyList(), iPage.getTotal());
        }
        List<UserVO> voList = new ArrayList<>();
        iPage.getRecords().forEach(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            voList.add(vo);
        });
        return ServicePageResult.buildSuccess(voList, iPage.getTotal());
    }
}
