package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.RoleResourceDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface RoleResourceMapper extends BaseMapper<RoleResourceDO> {

    /**
     * 获取菜单资源 id 列表
     *
     * @param roleId roleId
     * @return list
     */
    List<Long> listMenuResourceId(@Param(value = "roleId") Long roleId);

    /**
     * 获取按钮资源列表
     *
     * @param roleId roleId
     * @return list
     */
    List<String> listButtonResource(@Param(value = "roleId") Long roleId);
}
