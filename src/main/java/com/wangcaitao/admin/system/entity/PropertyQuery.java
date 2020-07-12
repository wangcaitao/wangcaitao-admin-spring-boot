package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PropertyQuery extends BaseQuery {
    private static final long serialVersionUID = 8766748741054095207L;

    /**
     * 属性名
     */
    private String name;
}
