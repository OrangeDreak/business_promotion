package com.x.bp.core.service.product;

import com.x.bp.common.utils.Validator;
import com.x.bp.core.vo.product.PlatformVO;
import com.x.bp.dao.mapper.PlatformMapper;
import com.x.bp.dao.po.PlatformDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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

    public List<PlatformVO> listValidPlatform() {
        List<PlatformDO> platformDOList = listValidPlatformBySort();
        if (CollectionUtils.isEmpty(platformDOList)) {
            return Collections.emptyList();
        }
        List<PlatformVO> voList = new ArrayList<>();
        platformDOList.forEach(platform -> {
            PlatformVO vo = new PlatformVO();
            BeanUtils.copyProperties(platform, vo);
            voList.add(vo);
        });
        return voList;
    }

    public List<PlatformDO> listByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return platformMapper.selectBatchIds(ids);
    }
}
