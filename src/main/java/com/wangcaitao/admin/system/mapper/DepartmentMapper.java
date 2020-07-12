package com.wangcaitao.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.system.entity.DepartmentBO;
import com.wangcaitao.admin.system.entity.DepartmentDO;
import com.wangcaitao.admin.system.entity.DepartmentQuery;
import com.wangcaitao.admin.system.entity.DepartmentTreeBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangcaitao
 */
public interface DepartmentMapper extends BaseMapper<DepartmentDO> {

    /**
     * 获取下一级子集
     *
     * @param query query
     * @return list
     */
    List<DepartmentBO> listChildren(DepartmentQuery query);

    /**
     * 获取最大 sort
     *
     * @param parentId parentId
     * @return max sort
     */
    Integer getMaxSortByParentId(@Param(value = "parentId") Long parentId);

    /**
     * 获取临近实体
     *
     * @param parentId     parentId
     * @param sort         sort
     * @param moveTypeCode moveTypeCode
     * @return entity
     */
    DepartmentDO getAdjoinEntity(@Param(value = "parentId") Long parentId,
                                 @Param(value = "sort") Integer sort,
                                 @Param(value = "moveTypeCode") Integer moveTypeCode);

    /**
     * 获取树的子集
     *
     * @param parentId parentId
     * @return List
     */
    List<DepartmentTreeBO> listTreeChildren(@Param(value = "parentId") Long parentId);

    /**
     * 校验名称是否存在
     *
     * @param parentId parentId
     * @param id       id
     * @param name     name
     * @return int
     */
    int validateNameExist(@Param(value = "parentId") Long parentId,
                          @Param(value = "id") Long id,
                          @Param(value = "name") String name);
}
