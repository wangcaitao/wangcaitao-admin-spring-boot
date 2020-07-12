package com.wangcaitao.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wangcaitao.common.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "employee")
public class EmployeeDO extends BaseDO {
    private static final long serialVersionUID = -7920632634527568193L;

    /**
     * 帐号状态编码. @see SystemDictCodeConstant.ACCOUNT_STATUS
     */
    private Integer accountStatusCode;

    /**
     * 状态编码. @see SystemDictCodeConstant.EMPLOYEE_STATUS
     */
    private Integer statusCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 头像
     */
    private String avatarImgUrl;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别编码
     */
    private Integer genderCode;
}
