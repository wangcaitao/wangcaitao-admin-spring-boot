package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.system.constant.SystemCacheEnum;
import com.wangcaitao.admin.system.entity.MenuBO;
import com.wangcaitao.admin.system.entity.ResourceTreeBO;
import com.wangcaitao.admin.system.entity.RoleResourceDO;
import com.wangcaitao.admin.system.mapper.RoleResourceMapper;
import com.wangcaitao.admin.system.service.ResourceRelationService;
import com.wangcaitao.admin.system.service.ResourceService;
import com.wangcaitao.admin.system.service.RoleResourceService;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.ResultUtils;
import com.wangcaitao.starter.redis.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResourceDO> implements RoleResourceService {

    @Resource
    private ResourceService resourceService;

    @Resource
    private ResourceRelationService resourceRelationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long roleId, List<Long> resourceIds) {
        deleteByRoleId(roleId);

        if (CollectionUtils.isEmpty(resourceIds)) {
            return;
        }

        Set<RoleResourceDO> roleResourceDos = new HashSet<>(128);
        for (Long resourceId : resourceIds) {
            resourceService.validateIdInvalid(resourceId);

            if (resourceRelationService.hasChildren(resourceId)) {
                continue;
            }

            RoleResourceDO roleResourceDo = new RoleResourceDO();
            roleResourceDo.setRoleId(roleId);
            roleResourceDo.setResourceId(resourceId);

            roleResourceDos.add(roleResourceDo);
        }

        saveBatch(roleResourceDos, resourceIds.size());

        StringRedisUtils.delete(String.format(SystemCacheEnum.ROLE_RESOURCE_MENU.getKey(), roleId));
        StringRedisUtils.delete(String.format(SystemCacheEnum.ROLE_RESOURCE_BUTTON.getKey(), roleId));
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        Wrapper<RoleResourceDO> queryWrapper = new QueryWrapper<>(new RoleResourceDO())
                .eq("role_id", roleId);

        remove(queryWrapper);

        StringRedisUtils.delete(String.format(SystemCacheEnum.ROLE_RESOURCE_MENU.getKey(), roleId));
        StringRedisUtils.delete(String.format(SystemCacheEnum.ROLE_RESOURCE_BUTTON.getKey(), roleId));
    }

    @Override
    public List<MenuBO> listMenu(Long roleId) {
        if (null == roleId) {
            return new ArrayList<>();
        }

        List<MenuBO> menuBos;
        String key = String.format(SystemCacheEnum.ROLE_RESOURCE_MENU.getKey(), roleId);
        String value = StringRedisUtils.get(key);
        if (null == value) {
            List<Long> checkedResourceTreeIds = baseMapper.listMenuResourceId(roleId);
            List<ResourceTreeBO> resourceTreeBos = ResultUtils.getData(resourceService.tree());
            removeNotCheckedResource(resourceTreeBos, checkedResourceTreeIds);

            menuBos = JacksonUtils.convertArray(resourceTreeBos, MenuBO.class);

            StringRedisUtils.set(key, JacksonUtils.toJsonString(menuBos), SystemCacheEnum.ROLE_RESOURCE_MENU.getTimeout());
        } else {
            menuBos = JacksonUtils.parseArray(value, MenuBO.class);
        }

        return menuBos;
    }

    @Override
    public List<String> listAuthorizedResources(Long roleId) {
        if (null == roleId) {
            return new ArrayList<>();
        }

        List<String> authorizedResources;
        String key = String.format(SystemCacheEnum.ROLE_RESOURCE_BUTTON.getKey(), roleId);
        String value = StringRedisUtils.get(key);
        if (null == value) {
            authorizedResources = baseMapper.listButtonResource(roleId);

            StringRedisUtils.set(key, JacksonUtils.toJsonString(authorizedResources), SystemCacheEnum.ROLE_RESOURCE_BUTTON.getTimeout());
        } else {
            authorizedResources = JacksonUtils.parseArray(value, String.class);
        }

        return authorizedResources;
    }

    /**
     * 移除未勾选资源
     *
     * @param children               children
     * @param checkedResourceTreeIds checkedResourceTreeIds
     */
    private void removeNotCheckedResource(List<ResourceTreeBO> children, List<Long> checkedResourceTreeIds) {
        if (!CollectionUtils.isEmpty(children)) {
            Iterator<ResourceTreeBO> childrenIterator = children.iterator();
            while (childrenIterator.hasNext()) {
                ResourceTreeBO resourceTreeBo = childrenIterator.next();
                List<ResourceTreeBO> nextChildren = resourceTreeBo.getChildren();
                boolean notChecked = true;
                for (Long selectedResourceTreeId : checkedResourceTreeIds) {
                    if (Objects.equals(resourceTreeBo.getId(), selectedResourceTreeId)) {
                        notChecked = false;

                        break;
                    }
                }

                if (notChecked) {
                    childrenIterator.remove();
                }

                removeNotCheckedResource(nextChildren, checkedResourceTreeIds);
            }
        }
    }
}
