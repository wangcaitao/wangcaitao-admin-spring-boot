package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictQuery extends BaseQuery {
    private static final long serialVersionUID = 5164054702799460823L;

    /**
     * 父级编码
     */
    private String parentCode;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}
