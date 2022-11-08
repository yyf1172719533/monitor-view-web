package com.timain.monitor.convert;

import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.pojo.vo.ColumnVo;
import com.timain.monitor.pojo.vo.StateVo;
import com.timain.monitor.pojo.vo.ToolBarVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 10:50
 */
@SuppressWarnings({"all"})
public abstract class AbstractDataConvert<T> {

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected final T t;

    protected final List<Map<String, Object>> dataList;

    public AbstractDataConvert(T t, List<Map<String, Object>> dataList) {
        this.t = t;
        this.dataList = dataList;
    }

    private T mapToBean(Map<String, Object> map) {
        try {
            if (t instanceof StateVo) {
                return (T) StateVo.convertMapToBean(map);
            }
            if (t instanceof ToolBarVo) {
                return (T) ToolBarVo.convertMapToBean(map);
            }
            if (t instanceof ColumnVo) {
                return (T) ColumnVo.convertMapToBean(map);
            }
            return t;
        } catch (Exception e) {
            LOGGER.error("类型转换异常：{}", e.getMessage());
            throw new BusinessException(ErrorEnum.BEAN_CONVERT_TYPE_ERROR);
        }
    }

    /**
     * list数据根据某个属性转换成map
     * @param key key值
     * @return map集合数据
     */
    protected Map<String, List<T>> convertDataToMap(String key) {
        return dataList.stream().collect(Collectors.groupingBy(item -> Optional.ofNullable(item.get(key)).orElse("").toString()))
                .entrySet().stream().map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().stream().map(this::mapToBean).collect(Collectors.toList())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
