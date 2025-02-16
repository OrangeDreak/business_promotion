package com.x.bp.core.service.order;

import com.x.bp.dao.mapper.ProductSnapshotMapper;
import com.x.bp.dao.po.ProductSnapshotDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:01
 * @Description
 */
@Slf4j
@Component
public class ProductSnapshotService {

    @Resource
    private ProductSnapshotMapper productSnapshotMapper;

    public Boolean addProductSnapshot(ProductSnapshotDO productSnapshotDO) {
        productSnapshotMapper.insert(productSnapshotDO);

        return true;
    }
}
