package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentEmployeeQuery extends BaseQuery {
    private static final long serialVersionUID = 8283754383634750246L;

    /**
     * departmentId
     */
    @NotNull(message = "departmentId 不能为空")
    private Long departmentId;

    /**
     * 关联状态. 0: 未关联, 1: 已关联
     */
    private Integer relationStatusCode;

    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 手机号码
     */
    private String phoneNumber;
}
