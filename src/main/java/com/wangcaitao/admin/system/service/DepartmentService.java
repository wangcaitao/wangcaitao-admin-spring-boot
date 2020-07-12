package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.DepartmentBO;
import com.wangcaitao.admin.system.entity.DepartmentDO;
import com.wangcaitao.admin.system.entity.DepartmentQuery;
import com.wangcaitao.admin.system.entity.DepartmentSaveDTO;
import com.wangcaitao.admin.system.entity.DepartmentTreeBO;
import com.wangcaitao.admin.system.entity.DepartmentUpdateDTO;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface DepartmentService extends IService<DepartmentDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<DepartmentBO> getById(Long id);

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<DepartmentBO>> list(DepartmentQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(DepartmentSaveDTO entity);

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(DepartmentUpdateDTO entity);

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
     * 资源树
     *
     * @return Result
     */
    Result<List<DepartmentTreeBO>> tree();

    /**
     * 校验 id 是否有效
     *
     * @param id id
     */
    void validateIdInvalid(Long id);
}
