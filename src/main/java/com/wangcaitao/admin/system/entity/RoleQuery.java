package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQuery extends BaseQuery {
    private static final long serialVersionUID = -7352506386782090506L;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}
