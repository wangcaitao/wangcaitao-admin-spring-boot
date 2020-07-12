package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeBO;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeDO;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeQuery;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface DepartmentEmployeeMapper extends BaseMapper<DepartmentEmployeeDO> {

    /**
     * 列表
     *
     * @param query query
     * @return list
     */
    List<DepartmentEmployeeBO> list(DepartmentEmployeeQuery query);
}
