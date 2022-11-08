package com.timain.monitor.convert;

import com.timain.monitor.pojo.vo.ToolBarVo;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 10:45
 */
public class ToolDataConvert extends AbstractDataConvert<ToolBarVo> {

    public ToolDataConvert(ToolBarVo toolBarVo, List<Map<String, Object>> dataList) {
        super(toolBarVo, dataList);
    }

    /**
     * list数据根据某个属性转换成map
     *
     * @param key key值
     * @return map集合数据
     */
    @Override
    public Map<String, List<ToolBarVo>> convertDataToMap(String key) {
        return super.convertDataToMap(key);
    }
}
