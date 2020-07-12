package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DepartmentQuery extends BaseQuery {
    private static final long serialVersionUID = 4090512510568934492L;

    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;
}
