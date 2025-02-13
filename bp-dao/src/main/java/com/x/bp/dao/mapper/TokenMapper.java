package com.x.bp.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.bp.dao.po.TokenDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TokenMapper extends BaseMapper<TokenDO> {
    @Update("update bp_token set is_delete = 1 where user_id = #{userId}")
    void deleteByUserId(@Param("userId") Long userId);

    @Select("select * from bp_token where is_delete = 0 and token = #{token}")
    TokenDO getByToken(@Param("token") String token);
}