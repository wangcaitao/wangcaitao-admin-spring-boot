package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeRoleBO implements Serializable {
    private static final long serialVersionUID = -9212688153972357218L;

    /**
     * 角色 id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;
}
