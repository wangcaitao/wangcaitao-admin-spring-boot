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
public class RoleEmployeeDO extends BaseDO {
    private static final long serialVersionUID = -106602817299453716L;

    /**
     * role.id
     */
    private Long roleId;

    /**
     * employee.id
     */
    private Long employeeId;
}
