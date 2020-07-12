package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class DepartmentEmployeeBO implements Serializable {
    private static final long serialVersionUID = 630555705233893314L;

    /**
     * id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 员工 id
     */
    private Long employeeId;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 手机号码
     */
    private String phoneNumber;
}
