package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.constant.SystemCacheEnum;
import com.wangcaitao.admin.system.entity.DepartmentBO;
import com.wangcaitao.admin.system.entity.DepartmentDO;
import com.wangcaitao.admin.system.entity.DepartmentQuery;
import com.wangcaitao.admin.system.entity.DepartmentRelationDO;
import com.wangcaitao.admin.system.entity.DepartmentSaveDTO;
import com.wangcaitao.admin.system.entity.DepartmentTreeBO;
import com.wangcaitao.admin.system.entity.DepartmentUpdateDTO;
import com.wangcaitao.admin.system.mapper.DepartmentMapper;
import com.wangcaitao.admin.system.service.DepartmentEmployeeService;
import com.wangcaitao.admin.system.service.DepartmentRelationService;
import com.wangcaitao.admin.system.service.DepartmentService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentDO> implements DepartmentService {

    @Resource
    private DepartmentRelationService departmentRelationService;

    @Resource
    private DepartmentEmployeeService departmentEmployeeService;

    @Override
    public Result<DepartmentBO> getById(Long id) {
        DepartmentDO departmentDo = super.getById(id);

        return null == departmentDo ? ResultUtils.error(HttpStatusEnum.NOT_FOUND) : ResultUtils.success(JacksonUtils.convertObject(departmentDo, DepartmentBO.class));
    }

    @Override
    public Result<List<DepartmentBO>> list(DepartmentQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<DepartmentBO> departmentBos = baseMapper.listChildren(query);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), departmentBos);
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(DepartmentSaveDTO entity) {


        Long parentId = entity.getParentId();
        validateParentIdInvalid(parentId);
        validateNameExist(parentId, null, entity.getName());

        DepartmentDO departmentDo = JacksonUtils.convertObject(entity, DepartmentDO.class);

        departmentDo.setTopStatusCode(null == parentId ? IntegerConstant.ONE : IntegerConstant.ZERO);
        departmentDo.setSort(getSort(parentId));

        save(departmentDo);
        departmentRelationService.save(parentId, departmentDo.getId());
        StringRedisUtils.delete(SystemCacheEnum.DEPARTMENT_TREE.getKey());

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> update(DepartmentUpdateDTO entity) {
        Long id = entity.getId();
        DepartmentDO departmentDo = super.getById(id);
        if (null == departmentDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
        Long parentId = null;
        if (Objects.equals(departmentDo.getTopStatusCode(), IntegerConstant.ZERO)) {
            Wrapper<DepartmentRelationDO> queryWrapper = new QueryWrapper<>(new DepartmentRelationDO())
                    .select("parent_id")
                    .eq("depth", 1)
                    .eq("child_id", id);

            DepartmentRelationDO departmentRelationDo = departmentRelationService.getOne(queryWrapper);
            if (null == departmentRelationDo) {
                return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
            }

            parentId = departmentRelationDo.getParentId();
        }

        validateNameExist(parentId, id, entity.getName());

        if (updateById(JacksonUtils.convertObject(entity, DepartmentDO.class))) {
            StringRedisUtils.delete(SystemCacheEnum.DEPARTMENT_TREE.getKey());

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> delete(Long id) {
        List<Long> childIds = departmentRelationService.listChildIdByParentId(id);
        if (CollectionUtils.isEmpty(childIds)) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        } else {
            for (Long childId : childIds) {
                departmentEmployeeService.deleteByDepartmentId(childId);
                removeById(childId);
                departmentRelationService.deleteByChildId(childId);
            }

            StringRedisUtils.delete(SystemCacheEnum.DEPARTMENT_TREE.getKey());
        }

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> updateSort(UpdateSortDTO entity) {
        Long id = entity.getId();
        DepartmentDO currentEntity = super.getById(id);
        if (null == currentEntity) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        Integer currentSort = currentEntity.getSort();

        DepartmentDO adjoinEntity;
        Integer topStatusCode = currentEntity.getTopStatusCode();
        Integer moveTypeCode = entity.getMoveTypeCode();
        if (Objects.equals(topStatusCode, IntegerConstant.ONE)) {
            Wrapper<DepartmentDO> nextDepartmentQueryWrapper = new QueryWrapper<DepartmentDO>()
                    .select("id", "sort")
                    .eq("top_status_code", 1)
                    .lt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), "sort", currentSort)
                    .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), false, "sort")
                    .gt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), "sort", currentSort)
                    .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), true, "sort")
                    .last("limit 1");
            adjoinEntity = getOne(nextDepartmentQueryWrapper);
        } else {
            adjoinEntity = baseMapper.getAdjoinEntity(departmentRelationService.getParentIdByChildId(currentEntity.getId()), currentSort, moveTypeCode);
        }

        if (null == adjoinEntity) {
            ConditionUtils.judgeMoveTypeCode(moveTypeCode);
        } else {
            currentEntity.setSort(adjoinEntity.getSort());
            updateById(currentEntity);

            adjoinEntity.setSort(currentSort);
            updateById(adjoinEntity);

            StringRedisUtils.delete(SystemCacheEnum.DEPARTMENT_TREE.getKey());

        }

        return ResultUtils.success();
    }

    @Override
    public Result<List<DepartmentTreeBO>> tree() {
        List<DepartmentTreeBO> departmentTreeBos;
        String key = SystemCacheEnum.DEPARTMENT_TREE.getKey();
        String value = StringRedisUtils.get(key);
        if (null == value) {
            departmentTreeBos = listTreeChildren(null);

            StringRedisUtils.set(SystemCacheEnum.DEPARTMENT_TREE.getKey(), JacksonUtils.toJsonString(departmentTreeBos), SystemCacheEnum.DEPARTMENT_TREE.getTimeout());

        } else {
            departmentTreeBos = JacksonUtils.parseArray(value, DepartmentTreeBO.class);
        }

        return ResultUtils.success(departmentTreeBos);
    }

    @Override
    public void validateIdInvalid(Long id) {
        Wrapper<DepartmentDO> queryWrapper = new QueryWrapper<DepartmentDO>()
                .eq("id", id);

        if (count(queryWrapper) == 0) {
            throw new ResultException("departmentId 无效");
        }
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

        Wrapper<DepartmentDO> queryWrapper = new QueryWrapper<DepartmentDO>()
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
            Wrapper<DepartmentDO> queryWrapper = new QueryWrapper<DepartmentDO>()
                    .select("sort")
                    .eq("top_status_code", 1)
                    .orderByDesc("sort")
                    .last("limit 1");

            DepartmentDO departmentDo = getOne(queryWrapper);

            return null == departmentDo ? 1 : departmentDo.getSort() + 1;
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
    private List<DepartmentTreeBO> listTreeChildren(Long parentId) {
        List<DepartmentTreeBO> departmentTreeBos = baseMapper.listTreeChildren(parentId);
        if (!CollectionUtils.isEmpty(departmentTreeBos)) {
            for (DepartmentTreeBO departmentTreeBo : departmentTreeBos) {
                departmentTreeBo.setChildren(listTreeChildren(departmentTreeBo.getId()));
            }
        }

        return departmentTreeBos;
    }

    /**
     * 校验名称是否存在
     *
     * @param parentId parentId
     * @param id       id
     * @param name     name
     */
    private void validateNameExist(Long parentId, Long id, String name) {
        if (0 < baseMapper.validateNameExist(parentId, id, name)) {
            throw new ResultException("名称已存在");
        }
    }
}
