package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.PropertyBO;
import com.wangcaitao.admin.system.entity.PropertyDO;
import com.wangcaitao.admin.system.entity.PropertyQuery;
import com.wangcaitao.admin.system.entity.PropertySaveDTO;
import com.wangcaitao.admin.system.entity.PropertyUpdateDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface PropertyService extends IService<PropertyDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<PropertyBO> getById(Long id);

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<PropertyBO>> list(PropertyQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(PropertySaveDTO entity);

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(PropertyUpdateDTO entity);

    /**
     * 删除
     *
     * @param id id
     * @return Result
     */
    Result<Serializable> delete(Long id);

    /**
     * 根据属性名获取属性值
     *
     * @param name name
     * @return entity
     */
    String getValueByName(String name);
}
