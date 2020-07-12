package com.wangcaitao.admin.auth.constant;

/**
 * @author wangcaitao
 */
public enum AuthCacheEnum {

    /**
     * token 信息<br>
     * key: employee-token:{phoneNumber}<br>
     */
    TOKEN("employee-token:%s"),

    /**
     * 权限信息<br>
     * key: employee-authorized:{token}<br>
     */
    AUTHORIZED_TOKEN("employee-authorized:%s"),

    /**
     * 员工信息<br>
     * key: employee:{token}<br>
     */
    EMPLOYEE_TOKEN("employee:%s"),
    ;

    /**
     * key
     */
    private String key;

    AuthCacheEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
