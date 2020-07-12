package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.EmployeeBO;
import com.wangcaitao.admin.system.entity.EmployeeDO;
import com.wangcaitao.admin.system.entity.EmployeeQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface EmployeeMapper extends BaseMapper<EmployeeDO> {

    /**
     * 详情
     *
     * @param id id
     * @return entity
     */
    EmployeeBO getById(@Param(value = "id") Long id);

    /**
     * id 列表
     *
     * @param query query
     * @return list
     */
    List<Long> listIds(EmployeeQuery query);

    /**
     * 列表
     *
     * @param ids ids
     * @return list
     */
    List<EmployeeBO> listByIds(@Param(value = "ids") List<Long> ids);
}
