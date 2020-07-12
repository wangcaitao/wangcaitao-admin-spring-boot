package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.OperationLogBO;
import com.wangcaitao.admin.system.entity.OperationLogDO;
import com.wangcaitao.admin.system.entity.OperationLogQuery;
import com.wangcaitao.admin.system.entity.OperationLogSaveDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface OperationLogService extends IService<OperationLogDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<OperationLogBO> getById(Long id);

    /**
     * 分页查询
     *
     * @param query query
     * @return list
     */
    Result<List<OperationLogBO>> list(OperationLogQuery query);

    /**
     * 保存
     *
     * @param entity entity
     */
    void save(OperationLogSaveDTO entity);

    /**
     * 删除
     *
     * @param ids ids
     * @return Result
     */
    Result<Serializable> delete(List<Long> ids);

    /**
     * 清空. 保留最近一个月数据
     *
     * @return Result
     */
    Result<Serializable> empty();
}
