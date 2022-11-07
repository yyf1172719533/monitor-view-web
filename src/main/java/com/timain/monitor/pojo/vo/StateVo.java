package com.timain.monitor.pojo.vo;

import com.timain.monitor.constants.KeyConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * 状态栏配置对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 17:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateVo implements Serializable {

    private static final long serialVersionUID = -5993881859659332187L;

    private String sequence;

    private String stateEnname;

    private String stateValue;

    private String statePic;

    private String stateChname;

    public static StateVo convertMapToBean(Map<String, Object> map) {
        StateVo stateVo = new StateVo();
        stateVo.setSequence(String.valueOf(map.get(KeyConstants.SEQUENCE)));
        stateVo.setStateEnname(String.valueOf(map.get(KeyConstants.STATE_EN_NAME)));
        stateVo.setStateValue(String.valueOf(map.get(KeyConstants.STATE_VALUE)));
        stateVo.setStatePic(String.valueOf(map.get(KeyConstants.STATE_PIC)));
        stateVo.setStateChname(String.valueOf(map.get(KeyConstants.STATE_CH_NAME)));
        return stateVo;
    }
}
