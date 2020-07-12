package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeDepartmentBO implements Serializable {
    private static final long serialVersionUID = -4247787201229627482L;

    /**
     * 部门 id
     */
    private Long departmentId;

    /**
     * 部门名称
     */
    private String departmentName;
}
