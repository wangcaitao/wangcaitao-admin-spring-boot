package com.wangcaitao.admin.employee.entity;

import com.wangcaitao.common.constant.CommonRegexConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeAccountUpdateDTO implements Serializable {
    private static final long serialVersionUID = -5168849087680031522L;

    /**
     * id
     */
    private Long id;

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
     * 邮箱
     */
    @Pattern(regexp = CommonRegexConstant.EMAIL, message = "email 格式错误")
    private String email;

    /**
     * 性别编码
     */
    @NotNull(message = "genderCode 不能为空")
    private Integer genderCode;
}
