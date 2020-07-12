package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class DepartmentTreeBO implements Serializable {
    private static final long serialVersionUID = -7590856062945295804L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 子集
     */
    private List<DepartmentTreeBO> children;
}
