package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.constant.SystemCacheEnum;
import com.wangcaitao.admin.system.entity.DictBO;
import com.wangcaitao.admin.system.entity.DictDO;
import com.wangcaitao.admin.system.entity.DictQuery;
import com.wangcaitao.admin.system.entity.DictSaveDTO;
import com.wangcaitao.admin.system.entity.DictTreeBO;
import com.wangcaitao.admin.system.entity.DictUpdateDTO;
import com.wangcaitao.admin.system.mapper.DictMapper;
import com.wangcaitao.admin.system.service.DictService;
import com.wangcaitao.common.constant.CharConstant;
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
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, DictDO> implements DictService {

    @Override
    public Result<DictBO> getById(Long id) {
        DictDO dictDo = super.getById(id);

        return null == dictDo ? ResultUtils.error(HttpStatusEnum.NOT_FOUND) : ResultUtils.success(JacksonUtils.convertObject(dictDo, DictBO.class));
    }

    @Override
    public Result<List<DictBO>> list(DictQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();
            String parentCode = StringUtils.isEmpty(query.getParentCode()) ? "" : query.getParentCode();

            Wrapper<DictDO> queryWrapper = new QueryWrapper<DictDO>()
                    .eq("parent_code", parentCode)
                    .likeRight(StringUtils.isNotEmpty(query.getCode()), "code", query.getCode())
                    .likeRight(StringUtils.isNotEmpty(query.getName()), "name", query.getName())
                    .orderByAsc("sort")
                    .orderByDesc("id");

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<DictDO> dictDos = list(queryWrapper);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), JacksonUtils.convertArray(dictDos, DictBO.class));
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    public Result<Serializable> save(DictSaveDTO entity) {
        String parentCode = entity.getParentCode();
        String code = entity.getCode().replaceAll(CharConstant.UNDERLINE, CharConstant.HYPHEN);

        validateParentCodeInvalid(parentCode);
        validateCodeExist(null, parentCode, code);

        DictDO dictDo = JacksonUtils.convertObject(entity, DictDO.class);
        dictDo.setCode(code);

        if (StringUtils.isEmpty(parentCode)) {
            dictDo.setSort(1);
        } else {
            // region 查询父级编码下子集最大 sort
            Wrapper<DictDO> queryWrapper = new QueryWrapper<DictDO>()
                    .select("sort")
                    .eq("parent_code", parentCode)
                    .orderByDesc("sort")
                    .last("limit 1");

            DictDO maxSortDictDo = getOne(queryWrapper);
            if (null == maxSortDictDo) {
                dictDo.setSort(1);
            } else {
                dictDo.setSort(maxSortDictDo.getSort() + 1);
            }
            // endregion
        }

        save(dictDo);

        deleteCache(parentCode, code);

        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> update(DictUpdateDTO entity) {
        Long id = entity.getId();
        DictDO dictDo = super.getById(id);
        if (null == dictDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        String parentCode = dictDo.getParentCode();
        String code = entity.getCode();
        if (StringUtils.isEmpty(parentCode)) {
            if (StringUtils.isBlank(code)) {
                return ResultUtils.error("code 不能为空");
            }
        }

        code = code.replaceAll(CharConstant.UNDERLINE, CharConstant.HYPHEN);
        validateCodeExist(id, parentCode, code);

        DictDO updateDictDo = JacksonUtils.convertObject(entity, DictDO.class);
        updateDictDo.setCode(code);

        if (updateById(updateDictDo)) {
            String originCode = dictDo.getCode();
            if (StringUtils.isEmpty(parentCode)) {
                Wrapper<DictDO> childrenUpdateWrapper = new UpdateWrapper<DictDO>()
                        .set("parent_code", code)
                        .eq("parent_code", originCode);

                update(childrenUpdateWrapper);
            }

            deleteCache(parentCode, code);
            deleteCache(parentCode, originCode);

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    public Result<Serializable> delete(Long id) {
        DictDO dictDo = super.getById(id);
        if (null == dictDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (removeById(id)) {
            deleteCache(dictDo.getParentCode(), dictDo.getCode());
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

        Wrapper<DictDO> currentQueryWrapper = new QueryWrapper<DictDO>()
                .select("id", "parent_code", "sort")
                .eq("id", entity.getId());

        DictDO currentEntity = getOne(currentQueryWrapper);
        if (null == currentEntity) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        String parentCode = currentEntity.getParentCode();
        if (StringUtils.isEmpty(parentCode)) {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_MOVE);
        }

        Integer currentSort = currentEntity.getSort();
        Wrapper<DictDO> adjoinQueryWrapper = new QueryWrapper<DictDO>()
                .select("id", "sort")
                .eq("parent_code", parentCode)
                .lt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), "sort", currentSort)
                .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_UP, moveTypeCode), false, "sort")
                .gt(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), "sort", currentSort)
                .orderBy(Objects.equals(CommonDictCodeConstant.MOVE_TYPE_DOWN, moveTypeCode), true, "sort")
                .last("limit 1");
        DictDO adjoinEntity = getOne(adjoinQueryWrapper);
        if (null == adjoinEntity) {
            ConditionUtils.judgeMoveTypeCode(moveTypeCode);
        } else {
            DictDO updateCurrentEntity = new DictDO();
            updateCurrentEntity.setId(currentEntity.getId());
            updateCurrentEntity.setSort(adjoinEntity.getSort());
            updateById(updateCurrentEntity);

            adjoinEntity.setSort(currentSort);
            updateById(adjoinEntity);

            StringRedisUtils.delete(SystemCacheEnum.DICT_TREE.getKey());
            StringRedisUtils.delete(String.format(SystemCacheEnum.DICT.getKey(), parentCode));
        }

        return ResultUtils.success();
    }

    @Override
    public Result<String> getNameByCode(String parentCode, String codes) {
        if (StringUtils.isBlank(parentCode) || StringUtils.isBlank(codes)) {
            return ResultUtils.success("");
        }

        StringBuilder names = new StringBuilder();
        String[] codeArray = codes.split(CharConstant.COMMA);
        for (String code : codeArray) {
            String key = String.format(SystemCacheEnum.DICT.getKey(), parentCode + CharConstant.UNDERLINE + code);
            String value = StringRedisUtils.get(key);
            if (null == value) {
                Wrapper<DictDO> queryWrapper = new QueryWrapper<DictDO>()
                        .select("name")
                        .eq("parent_code", parentCode)
                        .eq("code", code);

                DictDO dictDo = getOne(queryWrapper);
                String name = "";
                if (null != dictDo) {
                    name = dictDo.getName();
                    names.append(name).append(CharConstant.COMMA);
                }

                StringRedisUtils.set(key, name, SystemCacheEnum.DICT.getTimeout());
            } else {
                if (StringUtils.isNotEmpty(value)) {
                    names.append(value).append(CharConstant.COMMA);
                }
            }
        }

        if (StringUtils.isNotEmpty(names)) {
            names.deleteCharAt(names.length() - 1);
        }

        return ResultUtils.success(names.toString());
    }

    @Override
    public Result<List<DictBO>> listByParentCode(String parentCode) {
        List<DictBO> dictBos;
        String key = String.format(SystemCacheEnum.DICT.getKey(), parentCode);
        String value = StringRedisUtils.get(key);
        if (null == value) {
            Wrapper<DictDO> queryWrapper = new QueryWrapper<DictDO>()
                    .select("parent_code", "code", "name")
                    .eq("parent_code", parentCode)
                    .orderByAsc("sort");

            dictBos = JacksonUtils.convertArray(list(queryWrapper), DictBO.class);

            StringRedisUtils.set(key, JacksonUtils.toJsonString(dictBos), SystemCacheEnum.DICT.getTimeout());
        } else {
            dictBos = JacksonUtils.parseArray(value, DictBO.class);
        }

        return ResultUtils.success(dictBos);
    }

    @Override
    public Result<List<DictTreeBO>> tree() {
        List<DictTreeBO> dictTreeBos;
        String key = SystemCacheEnum.DICT_TREE.getKey();
        String value = StringRedisUtils.get(key);
        if (null == value) {
            Wrapper<DictDO> queryWrapper = new QueryWrapper<DictDO>()
                    .select("code", "name")
                    .eq("parent_code", "")
                    .orderByAsc("sort");

            dictTreeBos = JacksonUtils.convertArray(list(queryWrapper), DictTreeBO.class);
            for (DictTreeBO dictTreeBo : dictTreeBos) {
                dictTreeBo.setChildren(JacksonUtils.convertArray(ResultUtils.getData(listByParentCode(dictTreeBo.getCode())), DictTreeBO.class));
            }

            StringRedisUtils.set(key, JacksonUtils.toJsonString(dictTreeBos), SystemCacheEnum.DICT_TREE.getTimeout());
        } else {
            dictTreeBos = JacksonUtils.parseArray(value, DictTreeBO.class);
        }

        return ResultUtils.success(dictTreeBos);
    }

    /**
     * 验证父级编码是否有效
     *
     * @param parentCode parentCode
     */
    private void validateParentCodeInvalid(String parentCode) {
        if (StringUtils.isNotEmpty(parentCode)) {
            Wrapper<DictDO> queryWrapper = new QueryWrapper<DictDO>()
                    .eq("parent_code", "")
                    .eq("code", parentCode);

            if (0 == count(queryWrapper)) {
                throw new ResultException("parentCode 无效");
            }
        }
    }

    /**
     * 验证编码是否存在
     *
     * @param id         id
     * @param parentCode parentCode
     * @param code       code
     */
    private void validateCodeExist(Long id, String parentCode, String code) {
        if (StringUtils.isEmpty(parentCode)) {
            parentCode = "";
        }

        Wrapper<DictDO> queryWrapper = new QueryWrapper<DictDO>()
                .eq("parent_code", parentCode)
                .eq("code", code)
                .ne(null != id, "id", id);

        if (count(queryWrapper) > 0) {
            throw new ResultException("编码已存在");
        }
    }

    /**
     * 获取关系编码
     *
     * @param parentCode parentCode
     * @param code       code
     * @return relationCode
     */
    private String getRelationCode(String parentCode, String code) {
        return StringUtils.isEmpty(parentCode) ? code : parentCode + CharConstant.UNDERLINE + code;
    }

    /**
     * 删除缓存数据
     *
     * @param parentCode parentCode
     * @param code       code
     */
    private void deleteCache(String parentCode, String code) {
        StringRedisUtils.delete(SystemCacheEnum.DICT_TREE.getKey());
        if (StringUtils.isEmpty(parentCode)) {
            Set<String> keys = StringRedisUtils.getStringRedisTemplate().keys(String.format(SystemCacheEnum.DICT.getKey(), code) + CharConstant.STAR);
            if (!CollectionUtils.isEmpty(keys)) {
                StringRedisUtils.getStringRedisTemplate().delete(keys);
            }
        } else {
            StringRedisUtils.delete(String.format(SystemCacheEnum.DICT.getKey(), parentCode));
            StringRedisUtils.delete(String.format(SystemCacheEnum.DICT.getKey(), parentCode + CharConstant.UNDERLINE + code));
        }
    }
}
