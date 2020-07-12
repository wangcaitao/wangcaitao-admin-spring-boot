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
@TableName(value = "role_resource")
public class ResourceRoleDO extends BaseDO {
    private static final long serialVersionUID = -3790193975843383761L;

    /**
     * role.id
     */
    private Long roleId;

    /**
     * resource.id
     */
    private Long resourceId;
}
