package com.x.bp.core.service.product;

import com.x.bp.common.utils.Validator;
import com.x.bp.dao.mapper.PlatformMapper;
import com.x.bp.dao.po.PlatformDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class PlatformService {
    @Resource
    private PlatformMapper platformMapper;

    public List<PlatformDO> listValidPlatformBySort() {
        return platformMapper.listValidPlatformBySort();
    }

    public PlatformDO getValidPlatformById(Long id) {
        if (!Validator.greaterZero(id)) {
            return null;
        }
        return platformMapper.getValidPlatformById(id);
    }
}
