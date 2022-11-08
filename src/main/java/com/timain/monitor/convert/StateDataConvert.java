package com.timain.monitor.convert;

import com.timain.monitor.pojo.vo.StateVo;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 10:44
 */
public class StateDataConvert extends AbstractDataConvert<StateVo> {

    public StateDataConvert(StateVo stateVo, List<Map<String, Object>> dataList) {
        super(stateVo, dataList);
    }

    /**
     * list数据根据某个属性转换成map
     *
     * @param key key值
     * @return map集合数据
     */
    @Override
    public Map<String, List<StateVo>> convertDataToMap(String key) {
        return super.convertDataToMap(key);
    }
}
