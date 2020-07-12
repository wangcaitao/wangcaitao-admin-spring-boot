package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class RoleEmployeeSaveDTO implements Serializable {
    private static final long serialVersionUID = 2759484479408219902L;

    /**
     * role.id
     */
    @NotNull(message = "roleId 不能为空")
    private Long roleId;

    /**
     * employee.id 集合
     */
    @NotNull(message = "employeeIds 不能为空")
    @Size(min = 1, message = "employeeIds 不能为空")
    private List<Long> employeeIds;
}
