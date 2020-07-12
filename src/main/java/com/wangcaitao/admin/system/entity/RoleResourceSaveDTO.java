package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class RoleResourceSaveDTO implements Serializable {
    private static final long serialVersionUID = 4568103450366900988L;

    /**
     * 角色 id
     */
    @NotNull(message = "roleId 不能为空")
    private Long roleId;

    /**
     * 资源 ids
     */
    private List<Long> resourceIds;
}
