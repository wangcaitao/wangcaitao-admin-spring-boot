package com.wangcaitao.admin.common.util;

import com.wangcaitao.admin.system.entity.DictBO;
import com.wangcaitao.admin.system.service.DictService;
import com.wangcaitao.common.util.ResultUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangcaitao
 */
@Component
public class DictUtils {

    /**
     * instance
     */
    private static DictUtils instance;

    @Resource
    private DictService dictService;

    @PostConstruct
    public void init() {
        instance = this;
    }

    /**
     * 根据编号获取名称
     *
     * @param parentCode parentCode
     * @param codes      codes
     * @return String
     */
    public static String getNameByCode(String parentCode, String codes) {
        return ResultUtils.getData(instance.dictService.getNameByCode(parentCode, codes));
    }

    /**
     * 根据父级编号获取子集
     *
     * @param parentCode 父级编号
     * @return List
     */
    public static List<DictBO> listByParentCode(String parentCode) {
        return ResultUtils.getData(instance.dictService.listByParentCode(parentCode));
    }
}
