package com.wangcaitao.admin.common.util;

import com.wangcaitao.admin.system.service.PropertyService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author wangcaitao
 */
@Component
public class PropertyUtils {

    /**
     * instance
     */
    private static PropertyUtils instance;

    @Resource
    private PropertyService propertyService;

    @PostConstruct
    public void init() {
        instance = this;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param name name
     * @return 属性值
     */
    public static String getValueByName(String name) {
        return instance.propertyService.getValueByName(name);
    }
}
