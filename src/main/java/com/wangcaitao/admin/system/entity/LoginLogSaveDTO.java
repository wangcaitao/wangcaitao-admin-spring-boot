package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class LoginLogSaveDTO implements Serializable {
    private static final long serialVersionUID = -5847378748768101009L;

    /**
     * 登陆人 id
     */
    private Long loginUserId;

    /**
     * 登陆人姓名
     */
    private String loginUserName;

    /**
     * 登陆人手机号码
     */
    private String loginUserPhoneNumber;

    /**
     * 状态编码. @see SystemDictCodeConstant.LOGIN_STATUS
     */
    private Integer statusCode;

    /**
     * 错误信息
     */
    private String errorMsg;
}
