package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class PositionEmployeeBO implements Serializable {
    private static final long serialVersionUID = 2904861609368074772L;

    /**
     * id
     */
    private Long id;

    /**
     * 角色名称
     */
    private String positionName;

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
