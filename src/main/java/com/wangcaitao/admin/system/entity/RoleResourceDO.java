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
public class RoleResourceDO extends BaseDO {
    private static final long serialVersionUID = 5437349517875279134L;

    /**
     * role.id
     */
    private Long roleId;

    /**
     * resource.id
     */
    private Long resourceId;
}
