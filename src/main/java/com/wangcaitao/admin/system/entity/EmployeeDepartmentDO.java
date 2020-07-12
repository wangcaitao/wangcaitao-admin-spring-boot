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
@TableName(value = "employee_department")
public class EmployeeDepartmentDO extends BaseDO {
    private static final long serialVersionUID = 8182182394616870601L;

    /**
     * 部门 id
     */
    private Long departmentId;

    /**
     * 员工 id
     */
    private Long employeeId;
}
