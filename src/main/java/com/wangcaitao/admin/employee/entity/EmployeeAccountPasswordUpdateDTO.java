package com.wangcaitao.admin.employee.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeAccountPasswordUpdateDTO implements Serializable {
    private static final long serialVersionUID = 8076871110739711797L;

    /**
     * 原密码
     */
    @NotBlank(message = "originalPassword 不能为空")
    private String originalPassword;

    /**
     * 新密码
     */
    @NotBlank(message = "newPassword 不能为空")
    @Size(min = 6, max = 16, message = "newPassword 最少 6 位, 最大 16 位")
    private String newPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "confirmPassword 不能为空")
    @Size(min = 6, max = 16, message = "confirmPassword 最少 6 位, 最大 16 位")
    private String confirmPassword;
}
