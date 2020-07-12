package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.constant.SystemCacheEnum;
import com.wangcaitao.admin.system.entity.PositionBO;
import com.wangcaitao.admin.system.entity.PositionDO;
import com.wangcaitao.admin.system.entity.PositionQuery;
import com.wangcaitao.admin.system.entity.PositionSaveDTO;
import com.wangcaitao.admin.system.entity.PositionUpdateDTO;
import com.wangcaitao.admin.system.mapper.PositionMapper;
import com.wangcaitao.admin.system.service.PositionEmployeeService;
import com.wangcaitao.admin.system.service.PositionService;
import com.wangcaitao.common.constant.CommonDictCodeConstant;
import com.wangcaitao.common.constant.HttpStatusEnum;
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

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class PositionServiceImpl extends ServiceImpl<PositionMapper, PositionDO> implements PositionService {

    @Resource
    private PositionEmployeeService positionEmployeeService;

    @Override
    public Result<PositionBO> getById(Long id) {
        PositionDO positionDo = super.getById(id);

        return null == positionDo ? ResultUtils.error(HttpStatusEnum.NOT_FOUND) : ResultUtils.success(JacksonUtils.convertObject(positionDo, PositionBO.class));
    }

    @Override
    public Result<List<PositionBO>> list(PositionQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Wrapper<PositionDO> queryWrapper = new QueryWrapper<PositionDO>()
                    .likeRight(StringUtils.isNotEmpty(query.getName()), "name", query.getName())
                    .orderByAsc("sort");

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<PositionDO> positionDos = list(queryWrapper);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), JacksonUtils.convertArray(positionDos, PositionBO.class));
        } else {
            List<PositionBO> positionBos;
            String key = SystemCacheEnum.POSITION_LIST.getKey();
            String value = StringRedisUtils.get(key);
            if (null == value) {
                Wrapper<PositionDO> queryWrapper = new QueryWrapper<PositionDO>()
                        .select("id", "name")
                        .orderByAsc("sort");

                positionBos = JacksonUtils.convertArray(list(queryWrapper), PositionBO.class);

                StringRedisUtils.set(key, JacksonUtils.toJsonString(positionBos), SystemCacheEnum.POSITION_LIST.getTimeout());
            } else {
                positionBos = JacksonUtils.parseArray(value, PositionBO.class);
            }

            return ResultUtils.success(positionBos);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(PositionSaveDTO entity) {
        validateNameExist(null, entity.getName());

        PositionDO positionDo = JacksonUtils.convertObject(entity, PositionDO.class);

        // region 查询最大 sort
        Wrapper<PositionDO> queryWrapper = new QueryWrapper<PositionDO>()
                .select("sort")
                .orderByDesc("sort")
                .last("limit 1");

        PositionDO maxSortPositionDo = getOne(queryWrapper);
        if (null == maxSortPositionDo) {
            positionDo.setSort(1);
        } else {
            positionDo.setSort(maxSortPositionDo.getSort() + 1);
        }
        // endregion

        save(positionDo);

        StringRedisUtils.delete(SystemCacheEnum.POSITION_LIST.getKey());

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> update(PositionUpdateDTO entity) {
        Long id = entity.getId();
        validateNameExist(id, entity.getName());
        PositionDO positionDo = super.getById(id);
        if (null == positionDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (updateById(JacksonUtils.convertObject(entity, PositionDO.class))) {
            StringRedisUtils.delete(SystemCacheEnum.POSITION_LIST.getKey());

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> delete(Long id) {
        PositionDO positionDo = super.getById(id);
        if (null == positionDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (removeById(id)) {
            positionEmployeeService.deleteByPositionId(id);

            StringRedisUtils.delete(SystemCacheEnum.POSITION_LIST.getKey());

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> updateSort(UpdateSortDTO entity) {
        entity.validate();
        Integer moveTypeCode = entity.getMoveTypeCode();

        PositionDO currentEntity = super.getById(entity.getId());
        if (null == currentEntity) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        Integer currentSort = currentEntity.getSort();
        Wrapper<PositionDO> queryWrapper = new QueryWrapper<PositionDO>()
                .select("id", "sort")
                .lt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), "sort", currentSort)
                .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), false, "sort")
                .gt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), "sort", currentSort)
                .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), true, "sort")
                .last("limit 1");
        PositionDO adjoinEntity = getOne(queryWrapper);
        if (null == adjoinEntity) {
            ConditionUtils.judgeMoveTypeCode(moveTypeCode);
        } else {
            currentEntity.setSort(adjoinEntity.getSort());
            updateById(currentEntity);

            adjoinEntity.setSort(currentSort);
            updateById(adjoinEntity);

            StringRedisUtils.delete(SystemCacheEnum.POSITION_LIST.getKey());
        }

        return ResultUtils.success();
    }

    @Override
    public void validateIdInvalid(Long id) {
        Wrapper<PositionDO> queryWrapper = new QueryWrapper<PositionDO>()
                .eq("id", id);

        if (count(queryWrapper) == 0) {
            throw new ResultException("职位 id 无效");
        }
    }

    /**
     * 校验名称是否存在
     *
     * @param id   id
     * @param name name
     */
    private void validateNameExist(Long id, String name) {
        Wrapper<PositionDO> queryWrapper = new QueryWrapper<PositionDO>()
                .eq("name", name)
                .ne(null != id, "id", id);

        if (count(queryWrapper) > 0) {
            throw new ResultException("名称已存在");
        }
    }
}
