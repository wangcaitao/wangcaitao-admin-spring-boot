package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeBO;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeDO;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeQuery;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeSaveDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface DepartmentEmployeeService extends IService<DepartmentEmployeeDO> {

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<DepartmentEmployeeBO>> list(DepartmentEmployeeQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(DepartmentEmployeeSaveDTO entity);

    /**
     * 删除
     *
     * @param ids ids
     * @return Result
     */
    Result<Serializable> delete(List<Long> ids);

    /**
     * 根据 departmentId 删除
     *
     * @param departmentId departmentId
     */
    void deleteByDepartmentId(Long departmentId);
}
