package com.wangcaitao.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wangcaitao.common.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "property")
public class PropertyDO extends BaseDO {
    private static final long serialVersionUID = -5538264447370077293L;

    /**
     * 属性名
     */
    private String name;

    /**
     * 属性值
     */
    private String value;

    /**
     * 说明
     */
    private String description;
}
