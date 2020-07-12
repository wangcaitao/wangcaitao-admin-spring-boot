package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.system.entity.ResourceRoleDO;
import com.wangcaitao.admin.system.mapper.ResourceRoleMapper;
import com.wangcaitao.admin.system.service.ResourceRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class ResourceRoleServiceImpl extends ServiceImpl<ResourceRoleMapper, ResourceRoleDO> implements ResourceRoleService {

    @Override
    public void deleteByResourceId(Long resourceId) {
        Wrapper<ResourceRoleDO> queryWrapper = new QueryWrapper<>(new ResourceRoleDO())
                .eq("resource_id", resourceId);

        remove(queryWrapper);
    }
}
