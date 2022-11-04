package com.timain.monitor.constants;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 15:00
 */
public class MonitorAlarmColumn implements Columns {

    /**
     * 获取表头
     *
     * @return 表头
     */
    @Override
    public String[] getTitles() {
        return new String[]{"专业", "活动告警数", "派单告警数", "关注告警数", "济南", "青岛", "淄博", "德州",
                "烟台", "潍坊", "济宁", "泰安", "临沂", "菏泽", "滨州", "东营", "威海", "枣庄", "日照", "莱芜", "聊城"};
    }

    /**
     * 获取字段列
     *
     * @return 字段列
     */
    @Override
    public String[] getColumns() {
        return new String[]{"spec_name", "activealarm", "sheetalarm", "00", "01", "02", "05", "09", "03", "04",
                "07", "08", "10", "12", "14", "15", "06", "11", "16", "17", "13"};
    }

    /**
     * 获取文件名
     *
     * @return 文件名
     */
    @Override
    public String getFileName() {
        return "全省各地市监控告警统计";
    }
}
