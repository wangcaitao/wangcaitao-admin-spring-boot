package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.DictBO;
import com.wangcaitao.admin.system.entity.DictQuery;
import com.wangcaitao.admin.system.entity.DictSaveDTO;
import com.wangcaitao.admin.system.entity.DictTreeBO;
import com.wangcaitao.admin.system.entity.DictUpdateDTO;
import com.wangcaitao.admin.system.service.DictService;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@RestController
public class DictController {

    @Resource
    private DictService dictService;

    @GetMapping(value = "/dict/{id}")
    public Result<DictBO> getById(@PathVariable(value = "id") Long id) {
        return dictService.getById(id);
    }

    @GetMapping(value = "/dict")
    public Result<List<DictBO>> list(DictQuery query) {
        return dictService.list(query);
    }

    @PostMapping(value = "/dict")
    public Result<Serializable> save(@Validated @RequestBody DictSaveDTO entity) {
        return dictService.save(entity);
    }

    @PutMapping(value = "/dict")
    public Result<Serializable> update(@Validated @RequestBody DictUpdateDTO entity) {
        return dictService.update(entity);
    }

    @DeleteMapping(value = "/dict/{id}")
    public Result<Serializable> delete(@PathVariable(value = "id") Long id) {
        return dictService.delete(id);
    }

    @PutMapping(value = "/dict-sort")
    public Result<Serializable> updateSort(@RequestBody UpdateSortDTO entity) {
        return dictService.updateSort(entity);
    }

    @GetMapping(value = "/dict-code/{parentCode}/{codes}")
    public Result<String> getNameByCode(@PathVariable(value = "parentCode") String parentCode, @PathVariable(value = "codes") String codes) {
        return dictService.getNameByCode(parentCode, codes);
    }

    @GetMapping(value = "/dict-code/{parentCode}")
    public Result<List<DictBO>> listByParentCode(@PathVariable(value = "parentCode") String parentCode) {
        return dictService.listByParentCode(parentCode);
    }

    @GetMapping(value = "/dict-tree")
    public Result<List<DictTreeBO>> tree() {
        return dictService.tree();
    }
}
