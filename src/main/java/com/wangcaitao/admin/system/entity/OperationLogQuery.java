package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OperationLogQuery extends BaseQuery {
    private static final long serialVersionUID = 4211231456769769774L;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作人手机号码
     */
    private String operatorPhoneNumber;

    /**
     * 状态编码
     */
    private Integer statusCode;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 开始操作时间
     */
    private LocalDateTime startCreateGmt;

    /**
     * 结束操作时间
     */
    private LocalDateTime endCreateGmt;
}
