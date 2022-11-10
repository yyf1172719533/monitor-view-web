package com.timain.monitor.pojo.dto;

import com.timain.monitor.constants.Const;
import com.timain.monitor.utils.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 17:23
 */
@Data
public class AlarmSetDto implements Serializable {

    private static final long serialVersionUID = -8138066645767146887L;

    private String hangToolBar;
    private int viewId = -1;
    private String viewName;
    private String windowUniqueKey;
    private int windowId = -1;
    private String windowName;
    private boolean showMk;
    private String creator;
    /**
     * 选择默认视图对应其他视图的创建者
     */
    private String ctor;
    private String accLoginName;
    private List<Map<String, Object>> mks;
    private String monitorViewName;
    private String keyNa;
    private String tab;
    private String keyCw;
    private String keyCwLabel;
    private String keyWi;
    private String keyWiLabel;
    private String keyDs;
    private String keyDsLabel;
    private String keyAs;
    private String keyAsLabel;
    private String keyPl;
    private String keyPlLabel;
    private String keyF;
    private String keyFLabel;
    private String keyTb;
    private String keyTbLabel;
    /**
     * 是否为当班、自定义功能标识
     */
    private boolean jdCmk;
    private String[] dCmks = Const.MKS;
    private Map<String, List<Map>> dcWins = null;
    private List<Map> customViews = null;
    private List<Map> dutyAlarmViews = null;
    /**
     * 是否为视图列表页面
     */
    private boolean viewPage = false;
    private boolean addWin = false;
    /**
     * 山东现场需求，新建视图时默认配置为不共享
     */
    private int isPublic = 0;
    private int windowNum = 1;
    private String windowEnables;
    private String windowDesc;
    private int windowEnable = 1;
    private int loadCustAlarm = 0;
    private String childViewName;
    private String displayColumns;
    private String alarmStatuses;
    private int preloadType;
    private int preloadPara;
    private String ruleIds;
    private String toolbars;
    private String dCmk;
    /**
     * 当班、自定义窗口同步参数[ke]
     */
    private String winSyn;

    public String getHangToolBar() {
        return hangToolBar;
    }

    public void setHangToolBar(String hangToolBar) {
        this.hangToolBar = hangToolBar;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        try {
            this.viewName = URLDecoder.decode(viewName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            this.viewName = viewName;
        }
    }

    public String getWindowUniqueKey() {
        return windowUniqueKey;
    }

    public void setWindowUniqueKey(String windowUniqueKey) {
        this.windowUniqueKey = windowUniqueKey;
    }

    public int getWindowId() {
        return windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    public String getWindowName() {
        return windowName;
    }

    public void setWindowName(String windowName) {
        try {
            this.windowName = java.net.URLDecoder.decode(windowName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            this.windowName = windowName;
        }
    }

    public boolean isShowMk() {
        return showMk;
    }

    public void setShowMk(boolean showMk) {
        this.showMk = showMk;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCtor() {
        return ctor;
    }

    public void setCtor(String ctor) {
        this.ctor = ctor;
    }

    public String getAccLoginName() {
        return accLoginName;
    }

    public void setAccLoginName(String accLoginName) {
        this.accLoginName = accLoginName;
    }

    public List<Map<String, Object>> getMks() {
        return mks;
    }

    public void setMks(List<Map<String, Object>> mks) {
        this.mks = mks;
    }

    public String getMonitorViewName() {
        return monitorViewName;
    }

    public void setMonitorViewName(String monitorViewName) {
        this.monitorViewName = monitorViewName;
    }

    public String getKeyNa() {
        return keyNa;
    }

    public void setKeyNa(String keyNa) {
        this.keyNa = keyNa;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    public String getKeyCw() {
        return keyCw;
    }

    public void setKeyCw(String keyCw) {
        this.keyCw = keyCw;
    }

    public String getKeyCwLabel() {
        return keyCwLabel;
    }

    public void setKeyCwLabel(String keyCwLabel) {
        this.keyCwLabel = keyCwLabel;
    }

    public String getKeyWi() {
        return keyWi;
    }

    public void setKeyWi(String keyWi) {
        this.keyWi = keyWi;
    }

    public String getKeyWiLabel() {
        return keyWiLabel;
    }

    public void setKeyWiLabel(String keyWiLabel) {
        this.keyWiLabel = keyWiLabel;
    }

    public String getKeyDs() {
        return keyDs;
    }

    public void setKeyDs(String keyDs) {
        this.keyDs = keyDs;
    }

    public String getKeyDsLabel() {
        return keyDsLabel;
    }

    public void setKeyDsLabel(String keyDsLabel) {
        this.keyDsLabel = keyDsLabel;
    }

    public String getKeyAs() {
        return keyAs;
    }

    public void setKeyAs(String keyAs) {
        this.keyAs = keyAs;
    }

    public String getKeyAsLabel() {
        return keyAsLabel;
    }

    public void setKeyAsLabel(String keyAsLabel) {
        this.keyAsLabel = keyAsLabel;
    }

    public String getKeyPl() {
        return keyPl;
    }

    public void setKeyPl(String keyPl) {
        this.keyPl = keyPl;
    }

    public String getKeyPlLabel() {
        return keyPlLabel;
    }

    public void setKeyPlLabel(String keyPlLabel) {
        this.keyPlLabel = keyPlLabel;
    }

    public String getKeyF() {
        return keyF;
    }

    public void setKeyF(String keyF) {
        this.keyF = keyF;
    }

    public String getKeyFLabel() {
        return keyFLabel;
    }

    public void setKeyFLabel(String keyFLabel) {
        this.keyFLabel = keyFLabel;
    }

    public String getKeyTb() {
        return keyTb;
    }

    public void setKeyTb(String keyTb) {
        this.keyTb = keyTb;
    }

    public String getKeyTbLabel() {
        return keyTbLabel;
    }

    public void setKeyTbLabel(String keyTbLabel) {
        this.keyTbLabel = keyTbLabel;
    }

    public boolean isJdCmk() {
        return jdCmk;
    }

    public void setJdCmk(boolean jdCmk) {
        this.jdCmk = jdCmk;
    }

    public String[] getdCmks() {
        return dCmks;
    }

    public void setdCmks(String[] dCmks) {
        this.dCmks = dCmks;
    }

    public Map<String, List<Map>> getDcWins() {
        return dcWins;
    }

    public void setDcWins(Map<String, List<Map>> dcWins) {
        this.dcWins = dcWins;
    }

    public List<Map> getCustomViews() {
        return customViews;
    }

    public void setCustomViews(List<Map> customViews) {
        this.customViews = customViews;
    }

    public List<Map> getDutyAlarmViews() {
        return dutyAlarmViews;
    }

    public void setDutyAlarmViews(List<Map> dutyAlarmViews) {
        this.dutyAlarmViews = dutyAlarmViews;
    }

    public boolean isViewPage() {
        return viewPage;
    }

    public void setViewPage(boolean viewPage) {
        this.viewPage = viewPage;
    }

    public boolean isAddWin() {
        return addWin;
    }

    public void setAddWin(boolean addWin) {
        this.addWin = addWin;
    }

    public int getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
    }

    public int getWindowNum() {
        return windowNum;
    }

    public void setWindowNum(int windowNum) {
        this.windowNum = windowNum;
    }

    public String getWindowEnables() {
        return windowEnables;
    }

    public void setWindowEnables(String windowEnables) {
        this.windowEnables = windowEnables;
    }

    public String getWindowDesc() {
        return windowDesc;
    }

    public void setWindowDesc(String windowDesc) {
        try {
            this.windowDesc = java.net.URLDecoder.decode(windowDesc, "utf-8");
        } catch (UnsupportedEncodingException e) {
            this.windowDesc = windowDesc;
        }
    }

    public int getWindowEnable() {
        return windowEnable;
    }

    public void setWindowEnable(int windowEnable) {
        this.windowEnable = windowEnable;
    }

    public int getLoadCustAlarm() {
        return loadCustAlarm;
    }

    public void setLoadCustAlarm(int loadCustAlarm) {
        this.loadCustAlarm = loadCustAlarm;
    }

    public String getChildViewName() {
        return childViewName;
    }

    public void setChildViewName(String childViewName) {
        this.childViewName = childViewName;
    }

    public String getDisplayColumns() {
        return displayColumns;
    }

    public void setDisplayColumns(String displayColumns) {
        this.displayColumns = displayColumns;
    }

    public String getAlarmStatuses() {
        return alarmStatuses;
    }

    public void setAlarmStatuses(String alarmStatuses) {
        this.alarmStatuses = alarmStatuses;
    }

    public int getPreloadType() {
        return preloadType;
    }

    public void setPreloadType(int preloadType) {
        this.preloadType = preloadType;
    }

    public int getPreloadPara() {
        return preloadPara;
    }

    public void setPreloadPara(int preloadPara) {
        this.preloadPara = preloadPara;
    }

    public String getRuleIds() {
        return ruleIds;
    }

    public void setRuleIds(String ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getToolbars() {
        return toolbars;
    }

    public void setToolbars(String toolbars) {
        this.toolbars = toolbars;
    }

    public String getdCmk() {
        return dCmk;
    }

    public void setdCmk(String dCmk) {
        this.dCmk = dCmk;
    }

    public String getWinSyn() {
        return winSyn;
    }

    public void setWinSyn(String winSyn) {
        this.winSyn = winSyn;
    }

    /**
     * 为弹出窗口
     *
     * @return
     */
    public boolean isOpenWin() {
        return Const.IS_OPEN_WIN.equals(hangToolBar);
    }

    /**
     * 专题监控弹出窗口
     *
     * @return
     */
    public boolean isSpecialWin() {
        return !StringUtil.empty(getWindowUniqueKey());
    }

    /**
     * 多视图弹出窗口
     *
     * @return
     */
    public boolean isMutiView() {
        return 0 < getWindowId();
    }
}
