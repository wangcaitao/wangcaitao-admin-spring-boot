package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.EmployeeRoleDO;

/**
 * @author wangcaitao
 */
public interface EmployeeRoleService extends IService<EmployeeRoleDO> {

    /**
     * 保存
     *
     * @param employeeId employeeId
     * @param roleId     roleId
     */
    void save(Long employeeId, Long roleId);

    /**
     * 删除
     *
     * @param employeeId employeeId
     */
    void deleteByEmployeeId(Long employeeId);
}
