package com.timain.monitor.constants;

/**
 * 全专业告警概况表
 *
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 14:55
 */
public class AlarmOverviewColumn implements Columns {

    /**
     * 获取表头
     *
     * @return 表头
     */
    @Override
    public String[] getTitles() {
        return new String[]{"专业", "已监控网元数", "总告警数", "压缩后告警数", "标工后告警数", "关联后告警数", "标准严主数",
                "延时后告警数", "关注告警数", "派单告警数", "已派发工单数", "在途工单数", "已关闭工单数"};
    }

    /**
     * 获取字段列
     *
     * @return 字段列
     */
    @Override
    public String[] getColumns() {
        return new String[] {"spec_name","res_count","alarm_before_compress_count","alarm_count",
                "alarm_after_located_count","alarm_after_related_count","standarded_level_1_2_alarm_count",
                "alarm_after_delay_count","daily_duty_alarm_count","dispatched_sheet_alarm_count",
                "dispatched_sheet_count","processing_sheet_count","closeed_sheet_count"};
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    @Override
    public String getFileName() {
        return "全专业告警概况表";
    }
}
