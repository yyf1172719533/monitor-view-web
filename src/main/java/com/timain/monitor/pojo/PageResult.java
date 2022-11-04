package com.timain.monitor.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据返回的Code码
 *
 * @author zhurq
 * @Date 2022-10-8
 * @Des
 */
@Data
@NoArgsConstructor
public class PageResult extends DataResult {

    long pageIndex;

    long pageSize;

    long total;

    public PageResult(long pageIndex, long pageSize, long total) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
    }
}
