package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.ResourceBO;
import com.wangcaitao.admin.system.entity.ResourceDO;
import com.wangcaitao.admin.system.entity.ResourceQuery;
import com.wangcaitao.admin.system.entity.ResourceTreeBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface ResourceMapper extends BaseMapper<ResourceDO> {

    /**
     * 获取下一级子集
     *
     * @param query query
     * @return list
     */
    List<ResourceBO> listChildren(ResourceQuery query);

    /**
     * 获取最大 sort
     *
     * @param parentId parentId
     * @return max sort
     */
    Integer getMaxSortByParentId(@Param(value = "parentId") Long parentId);

    /**
     * 获取相邻实体
     *
     * @param parentId     parentId
     * @param sort         sort
     * @param moveTypeCode moveTypeCode
     * @return entity
     */
    ResourceDO getAdjoinEntity(@Param(value = "parentId") Long parentId,
                               @Param(value = "sort") Integer sort,
                               @Param(value = "moveTypeCode") Integer moveTypeCode);

    /**
     * 获取树的子集
     *
     * @param parentId parentId
     * @return List
     */
    List<ResourceTreeBO> listTreeChildren(@Param(value = "parentId") Long parentId);

    /**
     * 根据 childId 获取 parent name list
     *
     * @param childId childId
     * @return list
     */
    List<String> listParentResourceNameByChildId(@Param(value = "childId") Long childId);
}
