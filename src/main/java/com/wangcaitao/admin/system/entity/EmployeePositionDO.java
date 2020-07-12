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
@TableName(value = "employee_position")
public class EmployeePositionDO extends BaseDO {
    private static final long serialVersionUID = 7220216539934802186L;

    /**
     * employee.id
     */
    private Long employeeId;

    /**
     * position.id
     */
    private Long positionId;
}
