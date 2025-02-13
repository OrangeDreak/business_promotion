package com.x.bp.core.common;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Data
@ToString
@SuperBuilder
public class Page<T> {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 记录
     */
    private List<T> records;

    public Page(List<T> records, Long total) {
        this.total = total;
        this.records = records;
    }
}
