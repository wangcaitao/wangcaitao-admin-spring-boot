package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.EmployeeAccountStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeBO;
import com.wangcaitao.admin.system.entity.EmployeeDO;
import com.wangcaitao.admin.system.entity.EmployeeQuery;
import com.wangcaitao.admin.system.entity.EmployeeSaveDTO;
import com.wangcaitao.admin.system.entity.EmployeeStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeUpdatePasswordDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface EmployeeService extends IService<EmployeeDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<EmployeeBO> getById(Long id);

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<EmployeeBO>> list(EmployeeQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(EmployeeSaveDTO entity);

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(EmployeeUpdateDTO entity);

    /**
     * 重置密码
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updatePassword(EmployeeUpdatePasswordDTO entity);

    /**
     * 修改状态
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updateStatus(EmployeeStatusUpdateDTO entity);

    /**
     * 修改帐号状态
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updateAccountStatus(EmployeeAccountStatusUpdateDTO entity);

    /**
     * 校验 id 是否有效
     *
     * @param id id
     */
    void validateIdInvalid(Long id);
}
