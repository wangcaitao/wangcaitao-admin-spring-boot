package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceQuery extends BaseQuery {
    private static final long serialVersionUID = -5547488266780687323L;

    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 状态编码
     */
    private Integer statusCode;
}
