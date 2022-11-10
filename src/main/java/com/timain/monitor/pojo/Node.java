package com.timain.monitor.pojo;

import com.timain.monitor.utils.DataUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/9 15:40
 */
@Data
public class Node implements Serializable {

    private static final long serialVersionUID = -5913434963026315311L;

    /**
     * 子节点key值
     */
    private String nodeK;
    /**
     * 子节点val值
     */
    private String nodeV;
    /**
     * 子节点操作符<br/>
     * <li>%=</li> <li>=</li>
     */
    private String nodeOper;
    /**
     * 节点配置情况 1:配置/0:未配置
     */
    private String isConf = "0";

    public Node() {
    }

    public Node(String nodeK, String nodeV, String isConf) {
        this.nodeK = nodeK;
        this.nodeV = nodeV;
        this.isConf = isConf;
    }

    public String getNodeK() {
        return nodeK;
    }

    public void setNodeK(String nodeK) {
        this.nodeK = DataUtils.replaceSQ(nodeK);
    }

    public String getNodeV() {
        return nodeV;
    }

    public void setNodeV(String nodeV) {
        this.nodeV = DataUtils.replaceSQ(nodeV);
    }

    public String getNodeOper() {
        return nodeOper;
    }

    public void setNodeOper(String nodeOper) {
        this.nodeOper = nodeOper;
    }

    public String getIsConf() {
        return isConf;
    }

    public void setIsConf(String isConf) {
        this.isConf = isConf;
    }
}
