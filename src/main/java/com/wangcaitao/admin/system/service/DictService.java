package com.wangcaitao.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.system.entity.DictBO;
import com.wangcaitao.admin.system.entity.DictDO;
import com.wangcaitao.admin.system.entity.DictQuery;
import com.wangcaitao.admin.system.entity.DictSaveDTO;
import com.wangcaitao.admin.system.entity.DictTreeBO;
import com.wangcaitao.admin.system.entity.DictUpdateDTO;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
public interface DictService extends IService<DictDO> {

    /**
     * 详情
     *
     * @param id id
     * @return Result
     */
    Result<DictBO> getById(Long id);

    /**
     * 列表
     *
     * @param query query
     * @return Result
     */
    Result<List<DictBO>> list(DictQuery query);

    /**
     * 保存
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> save(DictSaveDTO entity);

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(DictUpdateDTO entity);

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
     * 根据编号获取名称
     *
     * @param parentCode parentCode
     * @param codes      codes
     * @return Result
     */
    Result<String> getNameByCode(String parentCode, String codes);

    /**
     * 根据父级编号获取子集
     *
     * @param parentCode 父级编号
     * @return Result
     */
    Result<List<DictBO>> listByParentCode(String parentCode);

    /**
     * 字典树
     *
     * @return Result
     */
    Result<List<DictTreeBO>> tree();
}
