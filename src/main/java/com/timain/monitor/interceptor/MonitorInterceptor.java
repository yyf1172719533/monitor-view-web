package com.timain.monitor.interceptor;

import com.timain.monitor.constants.SysConstants;
import com.timain.monitor.context.LoginContext;
import com.timain.monitor.enums.ErrorEnum;
import com.timain.monitor.exception.BusinessException;
import com.timain.monitor.exception.NoAuthException;
import com.timain.monitor.utils.DataUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/3 19:42
 */
public class MonitorInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Object userInfo = request.getSession().getAttribute(SysConstants.USER_KEY);
//        if (Objects.isNull(userInfo)) {
//            throw new NoAuthException(ErrorEnum.NO_AUTH);
//        }
//        Map<String, Object> info = DataUtils.parseUserInfo(String.valueOf(userInfo));
//        if (CollectionUtils.isEmpty(info)) {
//            throw new BusinessException(ErrorEnum.PARAM_ERROR);
//        }
//        LoginContext.set(info);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginContext.remove();
    }
}
