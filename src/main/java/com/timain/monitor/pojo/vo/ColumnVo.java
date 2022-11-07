package com.timain.monitor.pojo.vo;

import com.timain.monitor.constants.KeyConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 告警列配置对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 11:39
 */
@Data
public class ColumnVo implements Serializable {

    private static final long serialVersionUID = -527109769451L;

    private String id;

    private String name;

    private String usetype;

    private String sequence;

    public static ColumnVo convertMapToBean(Map<String, Object> map) {
        ColumnVo columnVo = new ColumnVo();
        columnVo.setId(String.valueOf(map.get(KeyConstants.FIELD_NAME)));
        columnVo.setName(String.valueOf(map.get(KeyConstants.FIELD_DESC)));
        columnVo.setUsetype(String.valueOf(map.get(KeyConstants.USE_TYPE)));
        columnVo.setSequence(String.valueOf(map.get(KeyConstants.SEQUENCE)));
        return columnVo;
    }
}
