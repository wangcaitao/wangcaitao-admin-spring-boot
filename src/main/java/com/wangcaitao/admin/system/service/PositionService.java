package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.PositionBO;
import com.wangcaitao.admin.system.entity.PositionDO;
import com.wangcaitao.admin.system.entity.PositionQuery;
import com.wangcaitao.admin.system.entity.PositionSaveDTO;
import com.wangcaitao.admin.system.entity.PositionUpdateDTO;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface PositionService extends IService<PositionDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<PositionBO> getById(Long id);

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<PositionBO>> list(PositionQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(PositionSaveDTO entity);

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(PositionUpdateDTO entity);

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
