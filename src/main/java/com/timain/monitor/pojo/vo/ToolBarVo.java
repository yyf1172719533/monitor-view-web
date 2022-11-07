package com.timain.monitor.pojo.vo;

import com.timain.monitor.constants.KeyConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 工具栏配置对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 10:18
 */
@Data
public class ToolBarVo implements Serializable {

    private static final long serialVersionUID = 4725218136981199302L;

    private String sequence;

    private String toolbarEnname;

    private String toolbarChname;

    public static ToolBarVo convertMapToBean(Map<String, Object> map) {
        ToolBarVo vo = new ToolBarVo();
        vo.setSequence(String.valueOf(map.get(KeyConstants.SEQUENCE)));
        vo.setToolbarEnname(String.valueOf(map.get(KeyConstants.TOOLBAR_EN_NAME)));
        vo.setToolbarChname(String.valueOf(map.get(KeyConstants.TOOLBAR_CH_NAME)));
        return vo;
    }
}
