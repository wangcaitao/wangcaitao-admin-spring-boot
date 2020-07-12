package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangcaitao
 */
@Data
public class DepartmentBO implements Serializable {
    private static final long serialVersionUID = -8328959661295730339L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createGmt;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedGmt;
}
