package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.system.entity.DepartmentRelationDO;
import com.wangcaitao.admin.system.mapper.DepartmentRelationMapper;
import com.wangcaitao.admin.system.service.DepartmentRelationService;
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
public class DepartmentRelationServiceImpl extends ServiceImpl<DepartmentRelationMapper, DepartmentRelationDO> implements DepartmentRelationService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long parentId, Long childId) {
        DepartmentRelationDO selfRelation = new DepartmentRelationDO();
        selfRelation.setParentId(childId);
        selfRelation.setChildId(childId);
        selfRelation.setDepth(0);

        save(selfRelation);

        if (null != parentId) {
            Wrapper<DepartmentRelationDO> queryWrapper = new QueryWrapper<DepartmentRelationDO>()
                    .select("parent_id", "depth")
                    .eq("child_id", parentId);

            List<DepartmentRelationDO> departmentRelationDos = list(queryWrapper);
            if (CollectionUtils.isEmpty(departmentRelationDos)) {
                return;
            }

            for (DepartmentRelationDO departmentRelationDo : departmentRelationDos) {
                DepartmentRelationDO otherRelation = new DepartmentRelationDO();
                otherRelation.setParentId(departmentRelationDo.getParentId());
                otherRelation.setChildId(childId);
                otherRelation.setDepth(departmentRelationDo.getDepth() + 1);

                save(otherRelation);
            }
        }
    }

    @Override
    public List<Long> listChildIdByParentId(Long parentId) {
        List<Long> childIds = new ArrayList<>();
        Wrapper<DepartmentRelationDO> queryWrapper = new QueryWrapper<DepartmentRelationDO>()
                .select("child_id")
                .eq("parent_id", parentId);

        List<DepartmentRelationDO> departmentRelationDos = list(queryWrapper);
        if (!CollectionUtils.isEmpty(departmentRelationDos)) {
            for (DepartmentRelationDO departmentRelationDo : departmentRelationDos) {
                childIds.add(departmentRelationDo.getChildId());
            }
        }

        return childIds;
    }

    @Override
    public void deleteByChildId(Long childId) {
        Wrapper<DepartmentRelationDO> deleteWrapper = new QueryWrapper<DepartmentRelationDO>()
                .eq("child_id", childId);

        remove(deleteWrapper);
    }

    @Override
    public Long getParentIdByChildId(Long childId) {
        Wrapper<DepartmentRelationDO> queryWrapper = new QueryWrapper<DepartmentRelationDO>()
                .select("parent_id")
                .eq("child_id", childId)
                .eq("depth", 1);

        DepartmentRelationDO departmentRelationDo = getOne(queryWrapper);
        if (null == departmentRelationDo) {
            throw new ResultException(HttpStatusEnum.NOT_FOUND);
        }

        return departmentRelationDo.getParentId();
    }
}
