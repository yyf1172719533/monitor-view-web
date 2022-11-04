package com.timain.monitor.constants;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 14:54
 */
public interface Columns {

    /**
     * 获取表头
     * @return 表头
     */
    String[] getTitles();

    /**
     * 获取字段列
     * @return 字段列
     */
    String[] getColumns();

    /**
     * 获取文件名
     * @return 文件名
     */
    String getFileName();
}
