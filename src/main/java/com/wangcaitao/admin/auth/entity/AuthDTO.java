package com.wangcaitao.admin.auth.entity;

import com.wangcaitao.common.constant.CommonRegexConstant;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class AuthDTO implements Serializable {
    private static final long serialVersionUID = 5109000715126628894L;

    /**
     * 手机号码
     */
    @NotNull(message = "phoneNumber 不能为空")
    @Pattern(regexp = CommonRegexConstant.PHONE_NUMBER, message = "phoneNumber 格式错误")
    private String phoneNumber;

    /**
     * 密码
     */
    @NotNull(message = "password 不能为空")
    private String password;
}
