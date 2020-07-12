package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class OperationLogSaveDTO implements Serializable {
    private static final long serialVersionUID = 7924672697519035769L;

    /**
     * 状态编码. @see SystemDictCodeConstant.LOGIN_STATUS
     */
    private Integer statusCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 耗时. 单位: 毫秒
     */
    private Long waitTime;
}
