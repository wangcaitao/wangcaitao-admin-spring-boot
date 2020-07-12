package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.constant.SystemCacheEnum;
import com.wangcaitao.admin.system.entity.PropertyBO;
import com.wangcaitao.admin.system.entity.PropertyDO;
import com.wangcaitao.admin.system.entity.PropertyQuery;
import com.wangcaitao.admin.system.entity.PropertySaveDTO;
import com.wangcaitao.admin.system.entity.PropertyUpdateDTO;
import com.wangcaitao.admin.system.mapper.PropertyMapper;
import com.wangcaitao.admin.system.service.PropertyService;
import com.wangcaitao.common.constant.CommonErrorEnum;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.ResultUtils;
import com.wangcaitao.starter.redis.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, PropertyDO> implements PropertyService {

    @Override
    public Result<PropertyBO> getById(Long id) {
        PropertyDO propertyDo = super.getById(id);

        return null == propertyDo ? ResultUtils.error(HttpStatusEnum.NOT_FOUND) : ResultUtils.success(JacksonUtils.convertObject(propertyDo, PropertyBO.class));
    }

    @Override
    public Result<List<PropertyBO>> list(PropertyQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Wrapper<PropertyDO> queryWrapper = new QueryWrapper<PropertyDO>()
                    .likeRight(StringUtils.isNotEmpty(query.getName()), "name", query.getName())
                    .orderByDesc("id");

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<PropertyDO> propertyDos = list(queryWrapper);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), JacksonUtils.convertArray(propertyDos, PropertyBO.class));
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    public Result<Serializable> save(PropertySaveDTO entity) {
        String name = entity.getName();
        validateNameExist(null, name);

        save(JacksonUtils.convertObject(entity, PropertyDO.class));

        StringRedisUtils.delete(String.format(SystemCacheEnum.PROPERTY.getKey(), name));

        return ResultUtils.success();
    }

    @Override
    public Result<Serializable> update(PropertyUpdateDTO entity) {
        Long id = entity.getId();
        String name = entity.getName();
        validateNameExist(id, name);
        PropertyDO propertyDo = super.getById(id);
        if (null == propertyDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (updateById(JacksonUtils.convertObject(entity, PropertyDO.class))) {
            StringRedisUtils.delete(String.format(SystemCacheEnum.PROPERTY.getKey(), propertyDo.getName()));
            if (StringUtils.isNotEmpty(name)) {
                StringRedisUtils.delete(String.format(SystemCacheEnum.PROPERTY.getKey(), name));
            }

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    public Result<Serializable> delete(Long id) {
        PropertyDO propertyDo = super.getById(id);
        if (null == propertyDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (removeById(id)) {
            StringRedisUtils.delete(String.format(SystemCacheEnum.PROPERTY.getKey(), propertyDo.getName()));

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    public String getValueByName(String name) {
        if (StringUtils.isBlank(name)) {
            return "";
        }

        String value = StringRedisUtils.get(String.format(SystemCacheEnum.PROPERTY.getKey(), name));
        if (null == value) {
            Wrapper<PropertyDO> queryWrapper = new QueryWrapper<PropertyDO>()
                    .select("value")
                    .eq("name", name);

            PropertyDO propertyDo = getOne(queryWrapper);
            value = null == propertyDo ? "" : propertyDo.getValue();

            StringRedisUtils.set(String.format(SystemCacheEnum.PROPERTY.getKey(), name), value, SystemCacheEnum.PROPERTY.getTimeout());
        }

        return value;
    }

    /**
     * 校验属性名是否存在
     *
     * @param id   id
     * @param name name
     */
    private void validateNameExist(Long id, String name) {
        Wrapper<PropertyDO> queryWrapper = new QueryWrapper<PropertyDO>()
                .eq("name", name)
                .ne(null != id, "id", id);

        if (count(queryWrapper) > 0) {
            throw new ResultException("属性名已存在");
        }
    }
}
