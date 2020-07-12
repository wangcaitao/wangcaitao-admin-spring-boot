package com.wangcaitao.admin.system.constant;

/**
 * @author wangcaitao
 */
public class SystemDictCodeConstant {

    // region 资源状态

    /**
     * 资源状态
     */
    public static final String RESOURCE_STATUS = "resource-status";

    /**
     * 资源状态 - 启用
     */
    public static final int RESOURCE_STATUS_ENABLE = 1;

    /**
     * 资源状态 - 禁用
     */
    public static final int RESOURCE_STATUS_DISABLE = 2;

    // endregion

    // region 资源类型

    /**
     * 资源类型
     */
    public static final String RESOURCE_TYPE = "resource-type";

    /**
     * 资源类型 - 菜单
     */
    public static final int RESOURCE_TYPE_MENU = 1;

    /**
     * 资源类型 - 按钮
     */
    public static final int RESOURCE_TYPE_BUTTON = 2;

    // endregion

    // region 请求方式

    /**
     * 请求方式
     */
    public static final String REQUEST_METHOD = "request-method";

    /**
     * 请求方式 - GET
     */
    public static final String REQUEST_METHOD_GET = "GET";

    /**
     * 请求方式 - POST
     */
    public static final String REQUEST_METHOD_POST = "POST";

    /**
     * 请求方式 - PUT
     */
    public static final String REQUEST_METHOD_PUT = "PUT";

    /**
     * 请求方式 - DELETE
     */
    public static final String REQUEST_METHOD_DELETE = "DELETE";

    // endregion

    // region 帐号状态

    /**
     * 帐号状态
     */
    public static final String ACCOUNT_STATUS = "account-status";

    /**
     * 帐号状态 - 启用
     */
    public static final int ACCOUNT_STATUS_ENABLE = 1;

    /**
     * 帐号状态 - 禁用
     */
    public static final int ACCOUNT_STATUS_DISABLE = 2;

    // endregion

    // region 员工状态

    /**
     * 员工状态
     */
    public static final String EMPLOYEE_STATUS = "employee-status";

    /**
     * 员工状态 - 在职
     */
    public static final int EMPLOYEE_STATUS_INCUMBENCY = 1;

    /**
     * 员工状态 - 离职
     */
    public static final int EMPLOYEE_STATUS_LEFT = 2;

    // endregion

    // region 登陆状态

    /**
     * 登陆状态
     */
    public static final String LOGIN_STATUS = "login-status";

    /**
     * 登陆状态 - 成功
     */
    public static final int LOGIN_STATUS_SUCCESS = 1;

    /**
     * 登陆状态 - 失败
     */
    public static final int LOGIN_STATUS_ERROR = 2;
    // endregion

    // region 操作状态

    /**
     * 操作状态
     */
    public static final String OPERATION_STATUS = "operation-status";

    /**
     * 操作状态 - 成功
     */
    public static final int OPERATION_STATUS_SUCCESS = 1;

    /**
     * 操作状态 - 失败
     */
    public static final int OPERATION_STATUS_ERROR = 2;
    // endregion
}
