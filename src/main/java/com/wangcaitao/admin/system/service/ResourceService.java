package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.ResourceBO;
import com.wangcaitao.admin.system.entity.ResourceDO;
import com.wangcaitao.admin.system.entity.ResourceQuery;
import com.wangcaitao.admin.system.entity.ResourceSaveDTO;
import com.wangcaitao.admin.system.entity.ResourceStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.ResourceTreeBO;
import com.wangcaitao.admin.system.entity.ResourceUpdateDTO;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface ResourceService extends IService<ResourceDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<ResourceBO> getById(Long id);

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<ResourceBO>> list(ResourceQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(ResourceSaveDTO entity);

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(ResourceUpdateDTO entity);

    /**
     * 删除
     *
     * @param id id
     * @return Result
     */
    Result<Serializable> delete(Long id);

    /**
     * 修改排序
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updateSort(UpdateSortDTO entity);

    /**
     * 更新状态
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updateStatus(ResourceStatusUpdateDTO entity);

    /**
     * 资源树
     *
     * @return Result
     */
    Result<List<ResourceTreeBO>> tree();

    /**
     * 校验 id 是否有效
     *
     * @param id id
     */
    void validateIdInvalid(Long id);

    /**
     * list parent name
     *
     * @param requestMethod requestMethod
     * @param url           url
     * @return list
     */
    List<String> listParentResourceNameByChildId(String requestMethod, String url);
}
