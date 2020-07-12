package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.RoleEmployeeBO;
import com.wangcaitao.admin.system.entity.RoleEmployeeDO;
import com.wangcaitao.admin.system.entity.RoleEmployeeQuery;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface RoleEmployeeMapper extends BaseMapper<RoleEmployeeDO> {

    /**
     * 列表
     *
     * @param query query
     * @return list
     */
    List<RoleEmployeeBO> list(RoleEmployeeQuery query);
}
