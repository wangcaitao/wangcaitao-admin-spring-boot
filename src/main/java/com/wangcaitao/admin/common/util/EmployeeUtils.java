package com.wangcaitao.admin.common.util;

import com.wangcaitao.admin.auth.constant.AuthCacheEnum;
import com.wangcaitao.admin.employee.entity.EmployeeAccountBO;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.starter.redis.StringRedisUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wangcaitao
 */
public class EmployeeUtils {

    /**
     * 获取 employee
     *
     * @return employee
     */
    public static EmployeeAccountBO getEmployee() {
        String token = HttpRequestUtils.getToken();

        String value = StringRedisUtils.get(String.format(AuthCacheEnum.EMPLOYEE_TOKEN.getKey(), token));
        if (StringUtils.isBlank(value)) {
            throw new ResultException(HttpStatusEnum.UNAUTHORIZED);
        }

        return JacksonUtils.parseObject(value, EmployeeAccountBO.class);
    }

    /**
     * 获取 employee id
     *
     * @return employeeId
     */
    public static Long getEmployeeId() {
        return getEmployee().getId();
    }

    /**
     * 获取手机号码
     *
     * @return 手机号码
     */
    public static String getEmployeePhoneNumber() {
        return getEmployee().getPhoneNumber();
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    public static String getEmployeeName() {
        return getEmployee().getName();
    }
}
