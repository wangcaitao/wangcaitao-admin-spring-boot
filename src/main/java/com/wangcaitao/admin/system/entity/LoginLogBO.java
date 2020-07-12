package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangcaitao
 */
@Data
public class LoginLogBO implements Serializable {
    private static final long serialVersionUID = -5043754907821322204L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户姓名
     */
    private String loginUserName;

    /**
     * 用户手机号码
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
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 状态
     */
    private String status;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 登陆时间
     */
    private LocalDateTime createGmt;
}
