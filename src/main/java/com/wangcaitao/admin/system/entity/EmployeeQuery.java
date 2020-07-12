package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeQuery extends BaseQuery {
    private static final long serialVersionUID = -8944245662541811460L;

    /**
     * 部门 id
     */
    private Long departmentId;

    /**
     * 状态编码
     */
    private String statusCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phoneNumber;
}
