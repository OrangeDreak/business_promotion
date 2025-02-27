package com.x.bp.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.bp.dao.po.UserDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<UserDO> {
    @Select("select * from bp_user where is_delete = 0 and (nickname = #{loginName} or email = #{loginName}) limit 1")
    UserDO getUserByLoginName(@Param("loginName") String loginName, @Param("userType") Integer userType);
}