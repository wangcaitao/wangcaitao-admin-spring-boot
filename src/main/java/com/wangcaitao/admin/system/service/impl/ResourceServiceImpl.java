package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.constant.SystemCacheEnum;
import com.wangcaitao.admin.system.constant.SystemDictCodeConstant;
import com.wangcaitao.admin.system.entity.ResourceBO;
import com.wangcaitao.admin.system.entity.ResourceDO;
import com.wangcaitao.admin.system.entity.ResourceQuery;
import com.wangcaitao.admin.system.entity.ResourceSaveDTO;
import com.wangcaitao.admin.system.entity.ResourceStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.ResourceTreeBO;
import com.wangcaitao.admin.system.entity.ResourceUpdateDTO;
import com.wangcaitao.admin.system.mapper.ResourceMapper;
import com.wangcaitao.admin.system.service.ResourceRelationService;
import com.wangcaitao.admin.system.service.ResourceRoleService;
import com.wangcaitao.admin.system.service.ResourceService;
import com.wangcaitao.common.constant.CharConstant;
import com.wangcaitao.common.constant.CommonDictCodeConstant;
import com.wangcaitao.common.constant.CommonErrorEnum;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.constant.IntegerConstant;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.ConditionUtils;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.ResultUtils;
import com.wangcaitao.starter.redis.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, ResourceDO> implements ResourceService {

    @Resource
    private ResourceRelationService resourceRelationService;

    @Resource
    private ResourceRoleService resourceRoleService;

    @Override
    public Result<ResourceBO> getById(Long id) {
        ResourceDO resourceDo = super.getById(id);

        return null == resourceDo ? ResultUtils.error(HttpStatusEnum.NOT_FOUND) : ResultUtils.success(JacksonUtils.convertObject(resourceDo, ResourceBO.class));
    }

    @Override
    public Result<List<ResourceBO>> list(ResourceQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<ResourceBO> resourceBos = baseMapper.listChildren(query);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), resourceBos);
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(ResourceSaveDTO entity) {
        Long parentId = entity.getParentId();
        validateParentIdInvalid(parentId);

        ResourceDO resourceDo = JacksonUtils.convertObject(entity, ResourceDO.class);

        resourceDo.setTopStatusCode(null == parentId ? IntegerConstant.ONE : IntegerConstant.ZERO);
        resourceDo.setSort(getSort(parentId));

        save(resourceDo);
        resourceRelationService.save(parentId, resourceDo.getId());
        StringRedisUtils.delete(SystemCacheEnum.RESOURCE_TREE.getKey());

        if (Objects.equals(entity.getTypeCode(), SystemDictCodeConstant.RESOURCE_TYPE_BUTTON)) {
            StringRedisUtils.delete(String.format(SystemCacheEnum.RESOURCE_RELATION.getKey(), resourceDo.getId()));
        }

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> update(ResourceUpdateDTO entity) {
        Long id = entity.getId();
        ResourceDO resourceDo = super.getById(id);
        if (null == resourceDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        ResourceDO updateResourceDo = JacksonUtils.convertObject(entity, ResourceDO.class);

        if (updateById(updateResourceDo)) {
            StringRedisUtils.delete(SystemCacheEnum.RESOURCE_TREE.getKey());

            if (Objects.equals(resourceDo.getTypeCode(), SystemDictCodeConstant.RESOURCE_TYPE_BUTTON)) {
                StringRedisUtils.delete(String.format(SystemCacheEnum.RESOURCE_RELATION.getKey(), id));
            }

            if (Objects.equals(entity.getTypeCode(), SystemDictCodeConstant.RESOURCE_TYPE_BUTTON)) {
                StringRedisUtils.delete(String.format(SystemCacheEnum.RESOURCE_RELATION.getKey(), id));
            }

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> delete(Long id) {
        List<Long> childIds = resourceRelationService.listChildIdByParentId(id);
        if (CollectionUtils.isEmpty(childIds)) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        } else {
            for (Long childId : childIds) {
                ResourceDO resourceDo = super.getById(childId);

                resourceRoleService.deleteByResourceId(childId);
                removeById(childId);
                resourceRelationService.deleteByChildId(childId);

                if (Objects.equals(resourceDo.getTypeCode(), SystemDictCodeConstant.RESOURCE_TYPE_BUTTON)) {
                    StringRedisUtils.delete(String.format(SystemCacheEnum.RESOURCE_RELATION.getKey(), childId));
                }
            }

            StringRedisUtils.delete(SystemCacheEnum.RESOURCE_TREE.getKey());
        }

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> updateSort(UpdateSortDTO entity) {
        Long id = entity.getId();
        ResourceDO currentEntity = super.getById(id);
        if (null == currentEntity) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        Integer currentSort = currentEntity.getSort();

        ResourceDO adjoinEntity;
        Integer topStatusCode = currentEntity.getTopStatusCode();
        Integer moveTypeCode = entity.getMoveTypeCode();
        if (Objects.equals(topStatusCode, IntegerConstant.ONE)) {
            Wrapper<ResourceDO> nextResourceQueryWrapper = new QueryWrapper<ResourceDO>()
                    .select("id", "sort")
                    .eq("top_status_code", 1)
                    .lt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), "sort", currentSort)
                    .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), false, "sort")
                    .gt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), "sort", currentSort)
                    .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), true, "sort")
                    .last("limit 1");
            adjoinEntity = getOne(nextResourceQueryWrapper);
        } else {
            adjoinEntity = baseMapper.getAdjoinEntity(resourceRelationService.getParentIdByChildId(currentEntity.getId()), currentSort, moveTypeCode);
        }

        if (null == adjoinEntity) {
            ConditionUtils.judgeMoveTypeCode(moveTypeCode);
        } else {
            currentEntity.setSort(adjoinEntity.getSort());
            updateById(currentEntity);

            adjoinEntity.setSort(currentSort);
            updateById(adjoinEntity);

            StringRedisUtils.delete(SystemCacheEnum.RESOURCE_TREE.getKey());

        }

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> updateStatus(ResourceStatusUpdateDTO entity) {
        List<Long> childIds = resourceRelationService.listChildIdByParentId(entity.getId());
        if (CollectionUtils.isEmpty(childIds)) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        } else {
            for (Long childId : childIds) {
                ResourceDO resourceDo = new ResourceDO();
                resourceDo.setId(childId);
                resourceDo.setStatusCode(entity.getStatusCode());

                updateById(resourceDo);
            }

            StringRedisUtils.delete(SystemCacheEnum.RESOURCE_TREE.getKey());

            return ResultUtils.success();
        }
    }

    @Override
    public Result<List<ResourceTreeBO>> tree() {
        List<ResourceTreeBO> resourceTreeBos;
        String key = SystemCacheEnum.RESOURCE_TREE.getKey();
        String value = StringRedisUtils.get(key);
        if (null == value) {
            resourceTreeBos = listTreeChildren(null);

            StringRedisUtils.set(SystemCacheEnum.RESOURCE_TREE.getKey(), JacksonUtils.toJsonString(resourceTreeBos), SystemCacheEnum.RESOURCE_TREE.getTimeout());

        } else {
            resourceTreeBos = JacksonUtils.parseArray(value, ResourceTreeBO.class);
        }

        return ResultUtils.success(resourceTreeBos);
    }

    @Override
    public void validateIdInvalid(Long id) {
        Wrapper<ResourceDO> queryWrapper = new QueryWrapper<ResourceDO>()
                .eq("id", id);

        if (count(queryWrapper) == 0) {
            throw new ResultException("resourceId 无效");
        }
    }

    @Override
    public List<String> listParentResourceNameByChildId(String requestMethod, String url) {
        Long resourceId = getResourceId(requestMethod, url);
        if (null == resourceId) {
            return new ArrayList<>();
        }

        List<String> resourceNames;
        String key = String.format(SystemCacheEnum.RESOURCE_RELATION.getKey(), resourceId);
        String value = StringRedisUtils.get(key);
        if (null == value) {
            resourceNames = baseMapper.listParentResourceNameByChildId(resourceId);

            StringRedisUtils.set(key, JacksonUtils.toJsonString(resourceNames), SystemCacheEnum.RESOURCE_RELATION.getTimeout());
        } else {
            resourceNames = JacksonUtils.parseArray(value, String.class);
        }

        return resourceNames;
    }

    /**
     * 查询按钮资源 id
     *
     * @param requestMethod requestMethod
     * @param url           url
     * @return id
     */
    private Long getResourceId(String requestMethod, String url) {
        if (StringUtils.isBlank(requestMethod) || StringUtils.isBlank(url)) {
            return null;
        }

        String[] urlArray = url.split(CharConstant.SLASH);
        int length = urlArray.length;
        if (1 == length) {
            return null;
        }

        Wrapper<ResourceDO> queryWrapper = new QueryWrapper<ResourceDO>()
                .select("id", "url")
                .eq("type_code", SystemDictCodeConstant.RESOURCE_TYPE_BUTTON)
                .eq("request_method", requestMethod)
                .likeRight("url", CharConstant.SLASH + urlArray[1]);

        List<ResourceDO> resourceDos = list(queryWrapper);
        if (CollectionUtils.isEmpty(resourceDos)) {
            return null;
        }

        Long resourceId = null;
        if (1 == resourceDos.size()) {
            resourceId = resourceDos.get(0).getId();
        } else {
            for (ResourceDO resourceDo : resourceDos) {
                if (resourceDo.getUrl().split(CharConstant.SLASH).length == length) {
                    resourceId = resourceDo.getId();

                    break;
                }
            }
        }

        return resourceId;
    }

    /**
     * 验证 parentId 是否有效
     *
     * @param parentId parentId
     */
    private void validateParentIdInvalid(Long parentId) {
        if (null == parentId) {
            return;
        }

        Wrapper<ResourceDO> queryWrapper = new QueryWrapper<ResourceDO>()
                .eq("id", parentId);

        if (count(queryWrapper) == 0) {
            throw new ResultException("parentId 无效");
        }
    }

    /**
     * 获取最大排序
     *
     * @param parentId parentId
     * @return sort
     */
    private int getSort(Long parentId) {
        if (null == parentId) {
            Wrapper<ResourceDO> queryWrapper = new QueryWrapper<ResourceDO>()
                    .select("sort")
                    .eq("top_status_code", 1)
                    .orderByDesc("sort")
                    .last("limit 1");

            ResourceDO resourceDo = getOne(queryWrapper);

            return null == resourceDo ? 1 : resourceDo.getSort() + 1;
        } else {
            Integer maxSort = baseMapper.getMaxSortByParentId(parentId);

            return null == maxSort ? 1 : maxSort + 1;
        }
    }

    /**
     * 获取树子集
     *
     * @param parentId parentId
     * @return List
     */
    private List<ResourceTreeBO> listTreeChildren(Long parentId) {
        List<ResourceTreeBO> resourceTreeBos = baseMapper.listTreeChildren(parentId);
        if (!CollectionUtils.isEmpty(resourceTreeBos)) {
            for (ResourceTreeBO resourceTreeBo : resourceTreeBos) {
                resourceTreeBo.setChildren(listTreeChildren(resourceTreeBo.getId()));
            }
        }

        return resourceTreeBos;
    }
}
