package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.LoginLogBO;
import com.wangcaitao.admin.system.entity.LoginLogDO;
import com.wangcaitao.admin.system.entity.LoginLogQuery;
import com.wangcaitao.admin.system.entity.LoginLogSaveDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface LoginLogService extends IService<LoginLogDO> {

    /**
     * 分页查询
     *
     * @param query query
     * @return list
     */
    Result<List<LoginLogBO>> list(LoginLogQuery query);

    /**
     * 保存
     *
     * @param entity entity
     */
    void save(LoginLogSaveDTO entity);

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
