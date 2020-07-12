package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.ResourceRelationDO;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface ResourceRelationService extends IService<ResourceRelationDO> {

    /**
     * 保存
     *
     * @param parentId parentId
     * @param childId  childId
     */
    void save(Long parentId, Long childId);

    /**
     * 根据 parentId 获取列表
     *
     * @param parentId parentId
     * @return list
     */
    List<Long> listChildIdByParentId(Long parentId);

    /**
     * 根据 childId 删除
     *
     * @param childId childId
     */
    void deleteByChildId(Long childId);

    /**
     * 根据 childId 获取 parentId
     *
     * @param childId childId
     * @return parentId
     */
    Long getParentIdByChildId(Long childId);

    /**
     * 判断当前资源有无子资源
     *
     * @param id id
     * @return true: 有子资源
     */
    boolean hasChildren(Long id);
}
