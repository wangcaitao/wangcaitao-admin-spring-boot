package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.EmployeeDepartmentDO;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface EmployeeDepartmentService extends IService<EmployeeDepartmentDO> {

    /**
     * 保存
     *
     * @param employeeId    employeeId
     * @param departmentIds departmentIds
     */
    void save(Long employeeId, List<Long> departmentIds);

    /**
     * 删除
     *
     * @param employeeId employeeId
     */
    void deleteByEmployeeId(Long employeeId);
}
