package com.wangcaitao.admin.system.constant;

import com.wangcaitao.common.constant.IntegerConstant;
import com.wangcaitao.common.util.RandomUtils;

/**
 * @author wangcaitao
 */
public enum SystemCacheEnum {

    /**
     * 字典<br>
     * key: dict:{relation_code}<br>
     * timeout: 30 天
     */
    DICT("dict:%s", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 字典树<br>
     * key: dict-tree<br>
     * timeout: 30 天
     */
    DICT_TREE("dict-tree", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 属性<br>
     * key: property:{name}<br>
     * timeout: 30 天
     */
    PROPERTY("property:%s", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 资源树<br>
     * key: resource-tree<br>
     * timeout: 30 天
     */
    RESOURCE_TREE("resource-tree", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 资源关系<br>
     * key: resource-relation:{resourceId}<br>
     * timeout: 30 天
     */
    RESOURCE_RELATION("resource-relation:%d", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 职位列表<br>
     * key: position-list<br>
     * timeout: 30 天
     */
    POSITION_LIST("position-list", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 角色列表<br>
     * key: role-list<br>
     * timeout: 30 天
     */
    ROLE_LIST("role-list", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 角色资源 - 菜单<br>
     * key: role-resource-menu:{roleId}<br>
     * timeout: 30 天
     */
    ROLE_RESOURCE_MENU("role-resource-menu:%d", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 角色资源 - 按钮<br>
     * key: role-resource-button:{roleId}<br>
     * timeout: 30 天
     */
    ROLE_RESOURCE_BUTTON("role-resource-button:%d", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),

    /**
     * 部门树<br>
     * key: resource-tree<br>
     * timeout: 30 天
     */
    DEPARTMENT_TREE("department-tree", 30 * 24 * 60 * 60L + RandomUtils.generateInterval(IntegerConstant.ZERO, IntegerConstant.ONE_HUNDRED)),
    ;

    /**
     * key
     */
    private String key;

    /**
     * timeout
     */
    private Long timeout;

    SystemCacheEnum(String key, Long timeout) {
        this.key = key;
        this.timeout = timeout;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }
}
