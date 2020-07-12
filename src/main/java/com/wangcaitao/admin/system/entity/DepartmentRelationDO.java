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
@TableName(value = "department_relation")
public class DepartmentRelationDO extends BaseDO {
    private static final long serialVersionUID = -2574461935364017349L;

    /**
     * 父节点 id
     */
    private Long parentId;

    /**
     * 子节点 id
     */
    private Long childId;

    /**
     * 深度
     */
    private Integer depth;
}
