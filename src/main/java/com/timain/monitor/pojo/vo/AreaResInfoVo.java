package com.timain.monitor.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 09:33
 */
@Data
public class AreaResInfoVo implements Serializable {

    private static final long serialVersionUID = -2688558741035792140L;

    /**
     * 视图JSON对象<br>
     * [{id:'',name:''}]
     */
    private String viewJson;

    /**
     * 窗口JSON对象<br>
     * [{id:'',name:''}]
     */
    private String winJson;

    /**
     * 状态栏JSON对象<br>
     * [{id:'',name:''}]
     */
    private String stateJson;

    /**
     * 工具栏JSON对象<br>
     * [{id:'',name:''}]
     */
    private String toolJson;

    /**
     * 工具栏JSON对象<br>
     * [{id:'',name:''}]
     */
    private String loadJson;

    /**
     * 工具栏JSON对象<br>
     * [{id:'',name:''}]
     */
    private String filterJson;

    /**
     * 权限内属地JSON对象<br>
     * [{id:'',name:''}]
     */
    private String cityJson;

    /**
     * 权限内专业JSON对象<br>
     * [{id:'',name:''}]
     */
    private String specJson;

    /**
     * 展示的自定义规则JSON对象<br>
     * [{id:'',name:''}]
     */
    private String ruleJson;

    /**
     * 可以配置的规则JSON对象 {create:[{id:'',name:''}],ispublic:[{id:'',name:''}]}
     */
    private String allRuleJson;

    /**
     * 权限内菜单JSON对象
     */
    private String menuJson;

    /**
     * 权限内各规则可展示告警列JSON对象<br>
     * {ruleId:[{id:'',name:''}]}
     */
    private String alarmColumnJson;

    /**
     * 声音配置对象 <br>
     * [{'specId':'','level':'','file':''}]
     */
    private String voiceConfigJson;

    /**
     * 专业id-专业名称映射Map<br>
     * {'specId':'specName','specId':'specName'}
     */
    private String specLabelMap;

    /**
     * 告警等级-颜色映射Map<br>
     * {'level':'color','level':'color'}
     */
    private String levelColorMap;

    /**
     * 各专业对应最大显示告警数量<br>
     * {ruleId:num,ruleId:num}
     */
    private String maxNumJson;
}
