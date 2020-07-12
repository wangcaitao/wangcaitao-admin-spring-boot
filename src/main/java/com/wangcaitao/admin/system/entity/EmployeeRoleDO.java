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
@TableName(value = "employee_role")
public class EmployeeRoleDO extends BaseDO {
    private static final long serialVersionUID = -4966681030942103140L;

    /**
     * employeeId
     */
    private Long employeeId;

    /**
     * roleId
     */
    private Long roleId;
}
