package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.EmployeePositionDO;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface EmployeePositionService extends IService<EmployeePositionDO> {

    /**
     * 保存
     *
     * @param employeeId  employeeId
     * @param positionIds positionIds
     */
    void save(Long employeeId, List<Long> positionIds);

    /**
     * 删除
     *
     * @param employeeId employeeId
     */
    void deleteByEmployeeId(Long employeeId);
}
