package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.ResourceRoleDO;

/**
 * @author wangcaitao
 */
public interface ResourceRoleService extends IService<ResourceRoleDO> {

    /**
     * 根据 resourceId 删除
     *
     * @param resourceId resourceId
     */
    void deleteByResourceId(Long resourceId);
}
