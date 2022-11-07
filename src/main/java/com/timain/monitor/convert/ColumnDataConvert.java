package com.timain.monitor.convert;

import com.timain.monitor.pojo.vo.ColumnVo;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 13:38
 */
public class ColumnDataConvert extends AbstractDataConvert<ColumnVo> {

    public ColumnDataConvert(ColumnVo columnVo, List<Map<String, Object>> dataList) {
        super(columnVo, dataList);
    }

    /**
     * list数据根据某个属性转换成map
     *
     * @return map集合数据
     */
    @Override
    public Map<String, List<ColumnVo>> convertDataToMap() {
        return super.convertDataToMap();
    }
}
