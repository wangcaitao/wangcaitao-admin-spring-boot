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
@TableName(value = "login_log")
public class LoginLogDO extends BaseDO {
    private static final long serialVersionUID = 929887682548981552L;

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
     * ip
     */
    private String ip;

    /**
     * 地理位置
     */
    private String location;

    /**
     * user-agent
     */
    private String userAgent;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 状态编码. @see SystemDictCodeConstant.LOGIN_STATUS
     */
    private Integer statusCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 错误信息
     */
    private String errorMsg;
}
