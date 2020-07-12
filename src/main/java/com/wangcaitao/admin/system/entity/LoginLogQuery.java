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
public class LoginLogQuery extends BaseQuery {
    private static final long serialVersionUID = 6345635245381131157L;

    /**
     * 用户姓名
     */
    private String loginUserName;

    /**
     * 用户手机号码
     */
    private String loginUserPhoneNumber;

    /**
     * 状态编码
     */
    private Integer statusCode;

    /**
     * 开始登陆时间
     */
    private LocalDateTime startCreateGmt;

    /**
     * 结束登陆时间
     */
    private LocalDateTime endCreateGmt;
}
