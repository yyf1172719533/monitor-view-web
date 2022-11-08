package com.timain.monitor.pojo.vo;

import com.timain.monitor.constants.KeyConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 告警图标对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 15:59
 */
@Data
public class WarnIconVo implements Serializable {

    private static final long serialVersionUID = -7768384631844916572L;

    private String stateDesc;

    private String statePic;

    public static WarnIconVo convertMapToBean(Map<String, Object> map) {
        WarnIconVo iconVo = new WarnIconVo();
        iconVo.setStateDesc(String.valueOf(map.get(KeyConstants.STATE_DESC)));
        iconVo.setStatePic(String.valueOf(map.get(KeyConstants.STATE_PIC)));
        return iconVo;
    }
}
