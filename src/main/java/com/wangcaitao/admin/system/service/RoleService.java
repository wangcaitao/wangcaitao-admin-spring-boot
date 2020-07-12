package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.RoleBO;
import com.wangcaitao.admin.system.entity.RoleDO;
import com.wangcaitao.admin.system.entity.RoleQuery;
import com.wangcaitao.admin.system.entity.RoleSaveDTO;
import com.wangcaitao.admin.system.entity.RoleUpdateDTO;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<RoleBO> getById(Long id);

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<RoleBO>> list(RoleQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(RoleSaveDTO entity);

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(RoleUpdateDTO entity);

    /**
     * 删除
     *
     * @param id id
     * @return Result
     */
    Result<Serializable> delete(Long id);

    /**
     * 修改排序
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updateSort(UpdateSortDTO entity);

    /**
     * 校验 id 是否有效
     *
     * @param id id
     */
    void validateIdInvalid(Long id);
}
