package com.x.bp.core.dto.order;

import com.x.bp.dao.po.OrderDO;
import com.x.bp.dao.po.OrderItemDO;
import com.x.bp.dao.po.ProductSnapshotDO;
import lombok.Data;

import java.util.List;

/**
 * @author zouzhe
 * @CreateDate 2025/2/16 15:32
 * @Description
 */
@Data
public class CreateOrderBO {
    private OrderDO orderDO;
    private List<OrderItemDO> orderItemDOS;
}
