package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.PositionEmployeeBO;
import com.wangcaitao.admin.system.entity.PositionEmployeeDO;
import com.wangcaitao.admin.system.entity.PositionEmployeeQuery;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface PositionEmployeeMapper extends BaseMapper<PositionEmployeeDO> {

    /**
     * 列表
     *
     * @param query query
     * @return list
     */
    List<PositionEmployeeBO> list(PositionEmployeeQuery query);
}
