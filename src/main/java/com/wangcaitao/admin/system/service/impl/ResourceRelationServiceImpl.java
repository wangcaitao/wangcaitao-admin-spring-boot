package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.system.entity.ResourceRelationDO;
import com.wangcaitao.admin.system.mapper.ResourceRelationMapper;
import com.wangcaitao.admin.system.service.ResourceRelationService;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.exception.ResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class ResourceRelationServiceImpl extends ServiceImpl<ResourceRelationMapper, ResourceRelationDO> implements ResourceRelationService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long parentId, Long childId) {
        ResourceRelationDO selfRelation = new ResourceRelationDO();
        selfRelation.setParentId(childId);
        selfRelation.setChildId(childId);
        selfRelation.setDepth(0);

        save(selfRelation);

        if (null != parentId) {
            Wrapper<ResourceRelationDO> queryWrapper = new QueryWrapper<ResourceRelationDO>()
                    .select("parent_id", "depth")
                    .eq("child_id", parentId);

            List<ResourceRelationDO> resourceRelationDos = list(queryWrapper);
            if (CollectionUtils.isEmpty(resourceRelationDos)) {
                return;
            }

            for (ResourceRelationDO resourceRelationDo : resourceRelationDos) {
                ResourceRelationDO otherRelation = new ResourceRelationDO();
                otherRelation.setParentId(resourceRelationDo.getParentId());
                otherRelation.setChildId(childId);
                otherRelation.setDepth(resourceRelationDo.getDepth() + 1);

                save(otherRelation);
            }
        }
    }

    @Override
    public List<Long> listChildIdByParentId(Long parentId) {
        List<Long> childIds = new ArrayList<>();
        Wrapper<ResourceRelationDO> queryWrapper = new QueryWrapper<ResourceRelationDO>()
                .select("child_id")
                .eq("parent_id", parentId);

        List<ResourceRelationDO> resourceRelationDos = list(queryWrapper);
        if (!CollectionUtils.isEmpty(resourceRelationDos)) {
            for (ResourceRelationDO resourceRelationDo : resourceRelationDos) {
                childIds.add(resourceRelationDo.getChildId());
            }
        }

        return childIds;
    }

    @Override
    public void deleteByChildId(Long childId) {
        Wrapper<ResourceRelationDO> deleteWrapper = new QueryWrapper<ResourceRelationDO>()
                .eq("child_id", childId);

        remove(deleteWrapper);
    }

    @Override
    public Long getParentIdByChildId(Long childId) {
        Wrapper<ResourceRelationDO> queryWrapper = new QueryWrapper<ResourceRelationDO>()
                .select("parent_id")
                .eq("child_id", childId)
                .eq("depth", 1);

        ResourceRelationDO resourceRelationDo = getOne(queryWrapper);
        if (null == resourceRelationDo) {
            throw new ResultException(HttpStatusEnum.NOT_FOUND);
        }

        return resourceRelationDo.getParentId();
    }

    @Override
    public boolean hasChildren(Long id) {
        Wrapper<ResourceRelationDO> queryWrapper = new QueryWrapper<ResourceRelationDO>()
                .eq("parent_id", id)
                .gt("depth", 0);

        return count(queryWrapper) > 0;
    }
}
