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
@TableName(value = "operation_log")
public class OperationLogDO extends BaseDO {
    private static final long serialVersionUID = 7511729221891674093L;

    /**
     * 操作人 id
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人手机号码
     */
    private String operatorPhoneNumber;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String type;

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

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求 content-type
     */
    private String requestContentType;

    /**
     * 请求 user-agent
     */
    private String requestUserAgent;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 耗时. 单位: 毫秒
     */
    private Long waitTime;
}
