package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.DepartmentRelationDO;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface DepartmentRelationService extends IService<DepartmentRelationDO> {

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
}
