package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PositionQuery extends BaseQuery {
    private static final long serialVersionUID = 7498785720183053857L;

    /**
     * 名称
     */
    private String name;
}
