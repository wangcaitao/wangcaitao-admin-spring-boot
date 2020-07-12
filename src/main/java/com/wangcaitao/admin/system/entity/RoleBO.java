package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class RoleBO implements Serializable {
    private static final long serialVersionUID = -6237040793577011413L;

    /**
     * id
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 已勾选资源 ids
     */
    private List<Long> resourceIds;

    /**
     * 创建时间
     */
    private LocalDateTime createGmt;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedGmt;
}
