package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.MenuBO;
import com.wangcaitao.admin.system.entity.RoleResourceDO;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface RoleResourceService extends IService<RoleResourceDO> {

    /**
     * 保存
     *
     * @param roleId      roleId
     * @param resourceIds resourceIds
     */
    void save(Long roleId, List<Long> resourceIds);

    /**
     * 根据 roleId 删除
     *
     * @param roleId roleId
     */
    void deleteByRoleId(Long roleId);

    /**
     * 根据 roleId 获取菜单
     *
     * @param roleId roleId
     * @return list
     */
    List<MenuBO> listMenu(Long roleId);

    /**
     * 根据 roleId 获取已授权资源
     *
     * @param roleId roleId
     * @return list
     */
    List<String> listAuthorizedResources(Long roleId);
}
