package com.timain.monitor.support.factory;

import com.timain.monitor.pojo.context.ResourceContext;
import com.timain.monitor.support.builder.ResourceBuilder;

/**
 * @author yyf
 * @version 1.0
 * @date 2022/11/7 21:53
 */
public class ResourceFactory {

    public static ResourceBuilder builder(ResourceContext context) {
        return ResourceBuilder.getInstance(context);
    }
}
