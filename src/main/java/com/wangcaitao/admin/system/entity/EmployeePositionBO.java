package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeePositionBO implements Serializable {
    private static final long serialVersionUID = -5120019156235105044L;

    /**
     * position.id
     */
    private Long positionId;

    /**
     * 职位名称
     */
    private String positionName;
}
