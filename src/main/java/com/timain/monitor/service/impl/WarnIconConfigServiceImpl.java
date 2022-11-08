package com.timain.monitor.service.impl;

import com.timain.monitor.constants.KeyConstants;
import com.timain.monitor.convert.WarnIconDataConvert;
import com.timain.monitor.mapper.WarnIconConfigMapper;
import com.timain.monitor.pojo.vo.WarnIconVo;
import com.timain.monitor.service.WarnIconConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 15:48
 */
@Service
public class WarnIconConfigServiceImpl implements WarnIconConfigService {

    @Autowired
    private WarnIconConfigMapper warnIconConfigMapper;

    /**
     * 查询告警图标列表
     *
     * @return 告警图标
     */
    @Override
    public Map<String, List<WarnIconVo>> queryWarnIconList() {
        List<Map<String, Object>> warnIconList = warnIconConfigMapper.selectWarnIconList();
        WarnIconDataConvert iconDataConvert = new WarnIconDataConvert(new WarnIconVo(), warnIconList);
        return iconDataConvert.convertDataToMap(KeyConstants.STATE_CH_NAME);
    }
}
