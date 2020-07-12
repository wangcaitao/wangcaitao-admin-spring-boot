package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.RoleEmployeeBO;
import com.wangcaitao.admin.system.entity.RoleEmployeeDO;
import com.wangcaitao.admin.system.entity.RoleEmployeeQuery;
import com.wangcaitao.admin.system.entity.RoleEmployeeSaveDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface RoleEmployeeService extends IService<RoleEmployeeDO> {

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<RoleEmployeeBO>> list(RoleEmployeeQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(RoleEmployeeSaveDTO entity);

    /**
     * 删除
     *
     * @param ids ids
     * @return Result
     */
    Result<Serializable> delete(List<Long> ids);

    /**
     * 根据 roleId 删除
     *
     * @param roleId roleId
     */
    void deleteByRoleId(Long roleId);
}
