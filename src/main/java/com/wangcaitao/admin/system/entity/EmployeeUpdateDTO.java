package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.constant.CommonRegexConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeUpdateDTO implements Serializable {
    private static final long serialVersionUID = 4458874995771947183L;

    /**
     * id
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 帐号状态
     */
    @NotNull(message = "accountStatusCode 不能为空")
    private Integer accountStatusCode;

    /**
     * 状态编码
     */
    @NotNull(message = "statusCode 不能为空")
    private Integer statusCode;

    /**
     * 姓名
     */
    @NotBlank(message = "name 不能为空")
    @Size(max = 20, message = "name 最大 20 个字符")
    private String name;

    /**
     * 手机号码
     */
    @NotBlank(message = "phoneNumber 不能为空")
    @Pattern(regexp = CommonRegexConstant.PHONE_NUMBER, message = "phoneNumber 格式错误")
    private String phoneNumber;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 邮箱
     */
    @Pattern(regexp = CommonRegexConstant.EMAIL, message = "email 格式错误")
    private String email;

    /**
     * 性别
     */
    @NotNull(message = "genderCode 不能为空")
    private Integer genderCode;

    /**
     * 部门 id
     */
    @NotNull(message = "departmentIds 不能为空")
    @Size(min = 1, message = "departmentIds 不能为空")
    private List<Long> departmentIds;

    /**
     * 职位 id
     */
    @NotNull(message = "positionIds 不能为空")
    @Size(min = 1, message = "positionIds 不能为空")
    private List<Long> positionIds;

    /**
     * 角色 id
     */
    private Long roleId;
}
