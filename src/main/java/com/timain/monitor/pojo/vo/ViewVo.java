package com.timain.monitor.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 视图对象
 * @author yyf
 * @version 1.0
 * @date 2022/11/6 13:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewVo implements Serializable {

    private static final long serialVersionUID = 1026719262641178653L;

    private String viewId;

    private String viewName;

    private String monitorViewname;

    private String creator;

    private String windownum;
}
