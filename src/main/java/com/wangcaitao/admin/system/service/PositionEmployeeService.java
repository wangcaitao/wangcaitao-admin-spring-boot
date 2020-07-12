package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.PositionEmployeeBO;
import com.wangcaitao.admin.system.entity.PositionEmployeeDO;
import com.wangcaitao.admin.system.entity.PositionEmployeeQuery;
import com.wangcaitao.admin.system.entity.PositionEmployeeSaveDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface PositionEmployeeService extends IService<PositionEmployeeDO> {

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<PositionEmployeeBO>> list(PositionEmployeeQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(PositionEmployeeSaveDTO entity);

    /**
     * 删除
     *
     * @param ids ids
     * @return Result
     */
    Result<Serializable> delete(List<Long> ids);

    /**
     * 根据 positionId 删除
     *
     * @param positionId positionId
     */
    void deleteByPositionId(Long positionId);
}
