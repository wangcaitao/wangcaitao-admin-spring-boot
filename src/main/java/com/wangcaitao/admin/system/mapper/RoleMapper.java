package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.RoleBO;
import com.wangcaitao.admin.system.entity.RoleDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangcaitao
 */
public interface RoleMapper extends BaseMapper<RoleDO> {

    /**
     * 详情
     *
     * @param id id
     * @return entity
     */
    RoleBO getById(@Param(value = "id") Long id);
}
