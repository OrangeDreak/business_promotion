package com.x.bp.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.bp.dao.po.PlatformDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PlatformMapper extends BaseMapper<PlatformDO> {
    @Select("select * from bp_platform where status = 1 order by sort asc, id desc ")
    List<PlatformDO> listValidPlatformBySort();

    @Select("select * from bp_platform where status = 1 and id = #{id}")
    PlatformDO getValidPlatformById(@Param("id") Long id);

}