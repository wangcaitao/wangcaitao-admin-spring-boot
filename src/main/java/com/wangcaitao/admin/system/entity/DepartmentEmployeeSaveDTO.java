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
public class DepartmentEmployeeSaveDTO implements Serializable {
    private static final long serialVersionUID = -8183839549920976276L;

    /**
     * department.id
     */
    @NotNull(message = "departmentId 不能为空")
    private Long departmentId;

    /**
     * employee.id 集合
     */
    @NotNull(message = "employeeIds 不能为空")
    @Size(min = 1, message = "employeeIds 不能为空")
    private List<Long> employeeIds;
}
