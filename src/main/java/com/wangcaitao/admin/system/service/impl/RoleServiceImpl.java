package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.constant.RoleConstant;
import com.wangcaitao.admin.system.constant.SystemCacheEnum;
import com.wangcaitao.admin.system.entity.RoleBO;
import com.wangcaitao.admin.system.entity.RoleDO;
import com.wangcaitao.admin.system.entity.RoleQuery;
import com.wangcaitao.admin.system.entity.RoleSaveDTO;
import com.wangcaitao.admin.system.entity.RoleUpdateDTO;
import com.wangcaitao.admin.system.mapper.RoleMapper;
import com.wangcaitao.admin.system.service.ResourceService;
import com.wangcaitao.admin.system.service.RoleEmployeeService;
import com.wangcaitao.admin.system.service.RoleResourceService;
import com.wangcaitao.admin.system.service.RoleService;
import com.wangcaitao.common.constant.CommonDictCodeConstant;
import com.wangcaitao.common.constant.CommonErrorEnum;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {

    @Resource
    private RoleResourceService roleResourceService;

    @Resource
    private RoleEmployeeService roleEmployeeService;

    @Resource
    private ResourceService resourceService;

    @Override
    public Result<RoleBO> getById(Long id) {
        RoleBO roleBo = baseMapper.getById(id);
        if (null == roleBo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        return ResultUtils.success(roleBo);
    }

    @Override
    public Result<List<RoleBO>> list(RoleQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Wrapper<RoleDO> queryWrapper = new QueryWrapper<RoleDO>()
                    .likeRight(StringUtils.isNotEmpty(query.getName()), "name", query.getName())
                    .likeRight(StringUtils.isNotEmpty(query.getCode()), "code", query.getCode())
                    .orderByAsc("sort");

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<RoleDO> roleDos = list(queryWrapper);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), JacksonUtils.convertArray(roleDos, RoleBO.class));
        } else {
            List<RoleBO> roleBos;
            String key = SystemCacheEnum.ROLE_LIST.getKey();
            String value = StringRedisUtils.get(key);
            if (null == value) {
                Wrapper<RoleDO> queryWrapper = new QueryWrapper<RoleDO>()
                        .select("id", "name")
                        .orderByAsc("sort");

                roleBos = JacksonUtils.convertArray(list(queryWrapper), RoleBO.class);

                StringRedisUtils.set(key, JacksonUtils.toJsonString(roleBos), SystemCacheEnum.ROLE_LIST.getTimeout());
            } else {
                roleBos = JacksonUtils.parseArray(value, RoleBO.class);
            }

            return ResultUtils.success(roleBos);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(RoleSaveDTO entity) {
        validateCodeExist(null, entity.getCode());

        RoleDO roleDo = JacksonUtils.convertObject(entity, RoleDO.class);

        // region 查询最大 sort
        Wrapper<RoleDO> queryWrapper = new QueryWrapper<RoleDO>()
                .select("sort")
                .orderByDesc("sort")
                .last("limit 1");

        RoleDO maxSortRoleDO = getOne(queryWrapper);
        if (null == maxSortRoleDO) {
            roleDo.setSort(1);
        } else {
            roleDo.setSort(maxSortRoleDO.getSort() + 1);
        }
        // endregion

        save(roleDo);

        roleResourceService.save(roleDo.getId(), entity.getResourceIds());

        StringRedisUtils.delete(SystemCacheEnum.ROLE_LIST.getKey());

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> update(RoleUpdateDTO entity) {
        Long id = entity.getId();
        validateCodeExist(id, entity.getCode());
        RoleDO roleDo = super.getById(id);
        if (null == roleDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (updateById(JacksonUtils.convertObject(entity, RoleDO.class))) {
            roleResourceService.save(id, entity.getResourceIds());

            StringRedisUtils.delete(SystemCacheEnum.ROLE_LIST.getKey());

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> delete(Long id) {
        if (Objects.equals(RoleConstant.SUPER_ADMIN_ID, id) || Objects.equals(RoleConstant.MASTER_ADMIN_ID, id) || Objects.equals(RoleConstant.SUB_ADMIN_ID, id)) {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_DELETE);
        }

        RoleDO roleDo = super.getById(id);
        if (null == roleDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (removeById(id)) {
            roleResourceService.deleteByRoleId(id);
            roleEmployeeService.deleteByRoleId(id);

            StringRedisUtils.delete(SystemCacheEnum.ROLE_LIST.getKey());

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    public Result<Serializable> updateSort(UpdateSortDTO entity) {
        entity.validate();
        Integer moveTypeCode = entity.getMoveTypeCode();

        RoleDO currentEntity = super.getById(entity.getId());
        if (null == currentEntity) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        Integer currentSort = currentEntity.getSort();
        Wrapper<RoleDO> queryWrapper = new QueryWrapper<RoleDO>()
                .select("id", "sort")
                .lt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), "sort", currentSort)
                .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), false, "sort")
                .gt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), "sort", currentSort)
                .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), true, "sort")
                .last("limit 1");
        RoleDO adjoinEntity = getOne(queryWrapper);
        if (null == adjoinEntity) {
            ConditionUtils.judgeMoveTypeCode(moveTypeCode);
        } else {
            currentEntity.setSort(adjoinEntity.getSort());
            updateById(currentEntity);

            adjoinEntity.setSort(currentSort);
            updateById(adjoinEntity);

            StringRedisUtils.delete(SystemCacheEnum.ROLE_LIST.getKey());
        }

        return ResultUtils.success();
    }

    @Override
    public void validateIdInvalid(Long id) {
        Wrapper<RoleDO> queryWrapper = new QueryWrapper<RoleDO>()
                .eq("id", id);

        if (count(queryWrapper) == 0) {
            throw new ResultException("角色 id 无效");
        }
    }

    /**
     * 校验编码是否存在
     *
     * @param id   id
     * @param code code
     */
    private void validateCodeExist(Long id, String code) {
        Wrapper<RoleDO> queryWrapper = new QueryWrapper<RoleDO>()
                .eq("code", code)
                .ne(null != id, "id", id);

        if (count(queryWrapper) > 0) {
            throw new ResultException("编码已存在");
        }
    }
}
